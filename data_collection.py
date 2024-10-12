import random
import time
from datetime import datetime
import joblib
import numpy as np
from pymongo import MongoClient
from sklearn.ensemble import RandomForestRegressor
from sklearn.model_selection import train_test_split

# MongoDB connection
client = MongoClient('mongodb://localhost:27017/')
db = client['predictive_maintenance']
collection = db['machine_data']

# ML model
model_filename = 'predictive_maintenance_model.joblib'

def train_model():
    # Fetch historical data from MongoDB
    historical_data = list(collection.find())

    if len(historical_data) < 100:  # Ensure we have enough data to train
        print("Not enough data to train the model. Collecting more data...")
        return None

    # Prepare data for training
    X = [[d['temperature'], d['vibration'], d['pressure']] for d in historical_data]
    y = [d.get('time_to_failure', random.uniform(0, 1000)) for d in historical_data]  # Simulated time to failure

    # Split data
    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)

    # Train model
    model = RandomForestRegressor(n_estimators=100, random_state=42)
    model.fit(X_train, y_train)

    # Save model
    joblib.dump(model, model_filename)
    print("Model trained and saved successfully.")
    return model

def load_or_train_model():
    try:
        model = joblib.load(model_filename)
        print("Model loaded successfully.")
    except:
        print("No existing model found. Training a new one...")
        model = train_model()
    return model

# Simulated IoT device
def generate_sensor_data():
    return {
        'temperature': round(random.uniform(20, 80), 2),
        'vibration': round(random.uniform(0, 10), 2),
        'pressure': round(random.uniform(0, 100), 2),
        'timestamp': datetime.utcnow()
    }

# Data collection, prediction, and storage loop
def collect_predict_and_store_data(model):
    while True:
        try:
            # Simulate data collection from multiple devices
            for device_id in range(1, 6):  # 5 devices
                data = generate_sensor_data()
                data['device_id'] = f'device_{device_id}'

                # Make prediction
                if model:
                    features = np.array([[data['temperature'], data['vibration'], data['pressure']]])
                    prediction = model.predict(features)[0]
                    data['predicted_time_to_failure'] = round(prediction, 2)

                # Store data in MongoDB
                collection.insert_one(data)
                print(f"Data stored for device {device_id}: {data}")

            # Wait for 5 seconds before next data collection
            time.sleep(5)

        except Exception as e:
            print(f"An error occurred: {e}")
            time.sleep(5)  # Wait before retrying

if __name__ == "__main__":
    print("Starting data collection, prediction, and storage...")
    model = load_or_train_model()
    collect_predict_and_store_data(model)
