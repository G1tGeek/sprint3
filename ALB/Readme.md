## 1. Create Target Groups

- **Go to EC2 → Target Groups → Create Target Group:**

| Target Group Name	| Protocol	| Port	| Target Type	 | Health Check Path |
|--|--|--|--|--|
| tg-frontend |	HTTP | 	3000 | 	IP	| / |
| tg-attendance-api	| HTTP	| 8080	| IP	| /api/v1/attendance/health/detail |
| tg-employee-api	| HTTP	| 8080	| IP	| /health |
| tg-salary-api |	HTTP |	8080 |	IP |	/actuator/health/ |


- **Register targets**


## 2. Create Internal Application Load Balancer

- **Go to EC2 → Load Balancers → Create Load Balancer**

- **Select Application Load Balancer**

- **Scheme: Internal**

- **IP address type: IPv4**

- **Availability Zones: Select zones where your targets live (us-east-1a, 1b)**

- **Listener: HTTP on port 80**

- **Wait for ALB to become active**

![Screenshot from 2025-06-05 20-05-04](https://github.com/user-attachments/assets/dcda5399-8a2e-4830-9dd4-41439e2f2efd)


## Hit DNS and see if frontend is available

![image](https://github.com/user-attachments/assets/3febdf4c-92c1-4933-9a7a-4d9b7bba8797)

# EOF
