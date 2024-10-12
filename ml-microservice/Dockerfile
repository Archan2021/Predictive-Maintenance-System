FROM python:3.9-slim

WORKDIR /app

COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

COPY . .

CMD ["python", "app.py"]

# !docker build -t ml-microservice .
# !docker run -p 5000:5000 ml-microservice

# Train the model:
# Send a POST request to http://localhost:5000/train with the following JSON body:
#? {
#? "data_path": "/path/to/your/training/data.csv"
#? }

# Make predictions:
# Send a POST request to http://localhost:5000/predict with the following JSON body:
#? {
#? "temperature": 45.5,
#? "vibration": 2.3,
#? "pressure": 101.2
#? }
