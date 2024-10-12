import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.ensemble import RandomForestRegressor
from sklearn.metrics import mean_squared_error
import joblib

class PredictiveMaintenanceModel:
    def __init__(self):
        self.model = None

    def train(self, data_path):
        # Load data
        data = pd.read_csv(data_path)

        # Prepare features and target
        X = data[['temperature', 'vibration', 'pressure']]
        y = data['time_to_failure']

        # Split data
        X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)

        # Train model
        self.model = RandomForestRegressor(n_estimators=100, random_state=42)
        self.model.fit(X_train, y_train)

        # Evaluate model
        y_pred = self.model.predict(X_test)
        mse = mean_squared_error(y_test, y_pred)
        print(f"Model MSE: {mse}")

    def predict(self, temperature, vibration, pressure):
        if self.model is None:
            raise Exception("Model not trained yet")

        features = [[temperature, vibration, pressure]]
        prediction = self.model.predict(features)
        return prediction[0]

    def save_model(self, path):
        joblib.dump(self.model, path)

    def load_model(self, path):
        self.model = joblib.load(path)
