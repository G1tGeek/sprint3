## **Dependencies**

### Run Time Dependencies
| Name           | Version  | Description                            | 
|----------------|----------|----------------------------------------|
| Python         | 3.10+    | Main programming language              | 
| pip            | latest   | For managing Python packages           | 
| Elasticsearch  | 7.17.17  | Used for indexing and querying employee data from the employee database    |

### Other Dependencies
| Name           | Description                                 | Why It’s Needed                                         |
|----------------|---------------------------------------------|--------------------------------------------------------|
| SMTP Email     | Used for sending scheduled notifications    | Sends email notifications                               |

**Important Ports**
| Inbound Traffic | Description                      |
|------------------|----------------------------------|
| 9200             | Elasticsearch HTTP Interface     |

**Others**
| Configuration         | Description                           |
|------------------------|---------------------------------------|
| App password / API key | Required for authenticating with SMTP |

---

## Step-by-step Installation

### Update System and Install Dependencies

```
sudo apt update
sudo apt install python3 python3-pip python3-venv curl wget git -y 
```
---

### Verify Python and pip

```
python3 --version
pip3 --version
```

![image](https://github.com/user-attachments/assets/2f20477a-98bb-4b45-8ac4-1ad90cde4ed5)

---

### Clone the Repository

```
git clone https://github.com/OT-MICROSERVICES/notification-worker.git
cd notification-worker/
```
![image](https://github.com/user-attachments/assets/8c01312e-e287-4a58-9f4d-07f6e5a6b255)

---

### Set Up Virtual Environment

```
python3 -m venv nvenv
source nvenv/bin/activate
```

![image](https://github.com/user-attachments/assets/ca4f579a-6f7f-4cc1-a148-9e254cad63c4)

---

### Install Python Dependencies

```
pip install -r requirements.txt
```

![image](https://github.com/user-attachments/assets/c02ed1e4-d309-4377-8153-74593c56ab90)

---

### Install Elasticsearch

```
wget https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-7.17.17-amd64.deb
sudo dpkg -i elasticsearch-7.17.17-amd64.deb
sudo systemctl enable elasticsearch
sudo systemctl start elasticsearch
```

![image](https://github.com/user-attachments/assets/f4cef397-8a30-4292-b99b-c63d56955b85)

---

### Verify Elasticsearch

```bash
curl http://localhost:9200
```

You should receive a JSON response from Elasticsearch like given below.

![image](https://github.com/user-attachments/assets/ce8efa4c-a9dd-4cba-b428-41b92e6d00e5)

---

## Configure & Run the Application

---

### Configure `config.yaml`

Edit `config.yaml` to match your credentials and environment setup:

```yaml
smtp:
  from: "your-real-email@gmail.com"
  username: "your-real-email@gmail.com"
  password: "your-app-password"  
  smtp_server: "smtp.gmail.com"
  smtp_port: "587"

elasticsearch:
  username: "elastic"
  password: "elastic"
  host: "localhost"
  port: 9200
```

![image](https://github.com/user-attachments/assets/6e81fa19-b919-4490-9bc6-3175de1d7a12)

> **Tip**: You must use a Gmail **App Password**. [How to create one](https://support.google.com/accounts/answer/185833)

---

### Add Test Data

Run the following command to insert test data into Elasticsearch:

```
curl -X POST "localhost:9200/employee-management/_doc/1" -H 'Content-Type: application/json' -u elastic:elastic -d'
{
  "name": "Your Name",
  "email_id": "your-real-email@gmail.com"
}
```

![image](https://github.com/user-attachments/assets/25348459-5d28-4bb1-886b-23913af2130e)

---

### Run the Notification Service

```
export CONFIG_FILE=./config.yaml
python3 notification_api.py --mode external
```

- Email has been succesfully sent to particular employee with the help of Notification-API.
  
![image](https://github.com/user-attachments/assets/3ab40d6b-164a-4523-9de8-adcfafe2dce6)

# EOF
