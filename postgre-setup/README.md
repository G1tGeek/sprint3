# **System Requirements**

| Component        | Minimum Requirement                           |
|------------------|-----------------------------------------------|
| OS               | Ubuntu (preferred) or Linux-based             |
| Disk Space       | 20 GB                                          |
| RAM              | 2 GB                                           |
| Processor        | 1 GHz Single-core  |
| Instance Type    | t2.medium or higher       |



### Install Postgres
We can install PostgreSQL in Window , Ubuntu and Mac etc. But As per our documentation we are moving forward with **ubuntu**.

#### STEP 1 : Update and install 

To install postgres directly with apt command

```
sudo apt update
sudo apt install postgresql postgresql-contrib -y
```

![image](https://github.com/user-attachments/assets/5158f997-9dd0-4d8b-9d67-fc6e581e64bb)

#### STEP 2 : To verify the installation and check the version of postgres

```
psql --version
```

![image](https://github.com/user-attachments/assets/90b0982d-8030-4ae8-aa82-a7a886334cf4)

#### STEP 3 :  To start, enable, and check the status of PostgreSQL


```
sudo systemctl status postgresql
```

![image](https://github.com/user-attachments/assets/87773ddf-c774-4fff-99f7-fa08c139a887)


**If inactive use `sudo systemctl start postgresql` and `sudo systemctl enable postgresql` and then check status.**


#### STEP 4 : For switching into postgres user

```
sudo -i -u postgres
```

![image](https://github.com/user-attachments/assets/b76be309-86cc-4656-b52c-9d464949c54a)

#### STEP 5 : To go into postgres shell

```
psql
```

![image](https://github.com/user-attachments/assets/a8c634c2-247e-4aea-b66f-d7548bb81f62)

# EOF
