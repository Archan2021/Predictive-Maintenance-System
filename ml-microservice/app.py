from flask import Flask, request, jsonify
from model import PredictiveMaintenanceModel

app = Flask(__name__)
model = PredictiveMaintenanceModel()

@app.route('/train', methods=['POST'])
def train_model():
    data_path = request.json['data_path']
    model.train(data_path)
    model.save_model('model.joblib')
    return jsonify({"message": "Model trained and saved successfully"})

@app.route('/predict', methods=['POST'])
def predict():
    data = request.json
    temperature = data['temperature']
    vibration = data['vibration']
    pressure = data['pressure']

    prediction = model.predict(temperature, vibration, pressure)
    return jsonify({"prediction": prediction})

if __name__ == '__main__':
    model.load_model('model.joblib')
    app.run(host='0.0.0.0', port=5000)
