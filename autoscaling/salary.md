# Creating Auto Scaling group for Salary-API instance

## Create Launch template from running Salary-API instance

- **Select `dev-Salary` -> `Actions` -> `Image and templates` -> `Create template from Instance`**

  ![image](https://github.com/user-attachments/assets/3025e199-3130-4289-81da-c66e07d57ce3)

- **Configure all settings and Click `Create launch template`**

  ![image](https://github.com/user-attachments/assets/dbbc3d3c-af67-4b2d-af83-3a14fea7051b)

## Create Auto Scaling Group

- **Go To `EC2` -> `Auto Scaling groups` -> `Create Auto Scaling group`**

- **Give ASG Name, Select `Launch template` and `Version` Click `Next`**

  ![image](https://github.com/user-attachments/assets/d5810f24-5a2e-474f-8114-8636799d913f)

- **Choose `VPC` and `Availablity Zones`**

  ![image](https://github.com/user-attachments/assets/176128bd-742c-4758-82ba-2edb43f8bda8)
