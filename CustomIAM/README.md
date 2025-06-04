## Global Read Access to all users 

### 1.  `IAM` -> `User groups` -> `Create group`

![image](https://github.com/user-attachments/assets/94c98b61-579d-4954-91ae-c006ee5523aa)

### 2. Add the users and Select `ReadOnlyAccess` -> `Filter by Type` = `AWS managed - job function`

![image](https://github.com/user-attachments/assets/617d0e7c-1fa6-47c1-af83-2550e6b371fa)

## Custom Policies for Users according to ticket requirements

### 1. Adil 

| Task Description                             | AWS Services Involved           | Required Access Level     |
| -------------------------------------------- | ------------------------------- | ------------------------- |
| Identify pre-existing IAM Policies           | IAM                             | **Read-only**             |
| Setup Network Skeleton for Dev (manual)      | VPC, Subnets, IGW, Route Tables | **Full access**           |
| Setup Route 53                               | Route 53                        | **Full access**           |
| Setup Infra for Salary (PostgreSQL instance) | EC2 Instances                   | **Create/manage**         |
| Setup Infra for Notification                 | Security Groups                 | **Create/manage**         |
| Setup PostgreSQL manually                    | Security Groups                 | **Already covered above** |

> adil.json

```
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Sid": "IAMReadOnly",
      "Effect": "Allow",
      "Action": [
        "iam:Get*",
        "iam:List*"
      ],
      "Resource": "*"
    },
    {
      "Sid": "NetworkInfrastructureAccess",
      "Effect": "Allow",
      "Action": [
        "ec2:CreateVpc",
        "ec2:DescribeVpcs",
        "ec2:DeleteVpc",
        "ec2:CreateSubnet",
        "ec2:DescribeSubnets",
        "ec2:DeleteSubnet",
        "ec2:CreateInternetGateway",
        "ec2:AttachInternetGateway",
        "ec2:DescribeInternetGateways",
        "ec2:DeleteInternetGateway",
        "ec2:CreateRouteTable",
        "ec2:AssociateRouteTable",
        "ec2:CreateRoute",
        "ec2:DescribeRouteTables",
        "ec2:DeleteRouteTable",
        "ec2:ReplaceRoute",
        "ec2:ReplaceRouteTableAssociation"
      ],
      "Resource": "*"
    },
    {
      "Sid": "Route53Access",
      "Effect": "Allow",
      "Action": [
        "route53:*"
      ],
      "Resource": "*"
    },
    {
      "Sid": "EC2InstanceAccess",
      "Effect": "Allow",
      "Action": [
        "ec2:RunInstances",
        "ec2:TerminateInstances",
        "ec2:StopInstances",
        "ec2:StartInstances",
        "ec2:RebootInstances",
        "ec2:DescribeInstances",
        "ec2:CreateTags",
        "ec2:DescribeImages"
      ],
      "Resource": "*"
    },
    {
      "Sid": "SecurityGroupAccess",
      "Effect": "Allow",
      "Action": [
        "ec2:CreateSecurityGroup",
        "ec2:DescribeSecurityGroups",
        "ec2:AuthorizeSecurityGroupIngress",
        "ec2:AuthorizeSecurityGroupEgress",
        "ec2:RevokeSecurityGroupIngress",
        "ec2:RevokeSecurityGroupEgress",
        "ec2:DeleteSecurityGroup"
      ],
      "Resource": "*"
    }
  ]
}
```

- **GoTo `IAM` -> `User` -> `Adil` -> `Add permissions` -> `Create inline policy` -> `JSON` and paste adil.json inside `Policy editor`**

  ![image](https://github.com/user-attachments/assets/0067d301-c4cf-455e-b931-899e8c88b18f)

- **Click `Next` -> Give a policy name ex: adil.json, then click `Create policy`**

  ![image](https://github.com/user-attachments/assets/aaf850e7-4573-4c16-92fa-23e5a7c8ab49)

- **Policy created ;)**

### 2. Durgesh

| Area                    | Services/Resources    | Access Level      |
| ----------------------- | --------------------- | ----------------- |
| IAM                     | Users, Groups         | **Create/manage** |
| Salary Infra            | EC2 + Security Groups | **Create/manage** |
| Notification Infra (LB) | ALB Listener Rules    | **Modify/create** |
| Redis Manual Setup      | EC2 Instances         | **Create/manage** |

> durgesh.json

```
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Sid": "IAMUserGroupManagement",
      "Effect": "Allow",
      "Action": [
        "iam:CreateUser",
        "iam:DeleteUser",
        "iam:ListUsers",
        "iam:GetUser",
        "iam:CreateGroup",
        "iam:DeleteGroup",
        "iam:ListGroups",
        "iam:AddUserToGroup",
        "iam:RemoveUserFromGroup"
      ],
      "Resource": "*"
    },
    {
      "Sid": "EC2InstanceAccess",
      "Effect": "Allow",
      "Action": [
        "ec2:RunInstances",
        "ec2:TerminateInstances",
        "ec2:DescribeInstances",
        "ec2:StartInstances",
        "ec2:StopInstances",
        "ec2:RebootInstances",
        "ec2:CreateTags",
        "ec2:DescribeImages"
      ],
      "Resource": "*"
    },
    {
      "Sid": "SecurityGroupAccess",
      "Effect": "Allow",
      "Action": [
        "ec2:CreateSecurityGroup",
        "ec2:DescribeSecurityGroups",
        "ec2:AuthorizeSecurityGroupIngress",
        "ec2:AuthorizeSecurityGroupEgress",
        "ec2:RevokeSecurityGroupIngress",
        "ec2:RevokeSecurityGroupEgress",
        "ec2:DeleteSecurityGroup"
      ],
      "Resource": "*"
    },
    {
      "Sid": "LoadBalancerListenerRuleAccess",
      "Effect": "Allow",
      "Action": [
        "elasticloadbalancing:CreateListener",
        "elasticloadbalancing:DeleteListener",
        "elasticloadbalancing:ModifyListener",
        "elasticloadbalancing:DescribeListeners",
        "elasticloadbalancing:CreateRule",
        "elasticloadbalancing:DeleteRule",
        "elasticloadbalancing:ModifyRule",
        "elasticloadbalancing:DescribeRules"
      ],
      "Resource": "*"
    }
  ]
}
```

- **GoTo `IAM` -> `User` -> `Durgesh` -> `Add permissions` -> `Create inline policy` -> `JSON` and paste durgesh.json inside `Policy editor`**

  ![image](https://github.com/user-attachments/assets/f65c7521-083e-40f0-832e-aae74e695936)

- **Click `Next` -> Give a policy name ex: durgesh.json, then click `Create policy`**

  ![image](https://github.com/user-attachments/assets/307101e7-ebe3-45a9-b6ca-ac20b9b96e61)

- **Policy created ;)**
  
### 3. Harsh

| Area           | Services/Resources                   | Access Level  |
| -------------- | ------------------------------------ | ------------- |
| IAM            | IAM Roles                            | Create/manage |
| Salary Infra   | ALB Listener Rules                   | Create/manage |
| Frontend Infra | Auto Scaling Group, Launch Templates | Create/manage |
| Redis Setup    | Security Groups                      | Create/manage |
| Implementation | Cost Allocation Tags                 | Manage/view   |

> harsh.json

```
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Sid": "IAMRoleManagement",
      "Effect": "Allow",
      "Action": [
        "iam:CreateRole",
        "iam:DeleteRole",
        "iam:GetRole",
        "iam:ListRoles",
        "iam:AttachRolePolicy",
        "iam:PutRolePolicy",
        "iam:UpdateAssumeRolePolicy"
      ],
      "Resource": "*"
    },
    {
      "Sid": "ALBListenerRules",
      "Effect": "Allow",
      "Action": [
        "elasticloadbalancing:CreateListener",
        "elasticloadbalancing:DeleteListener",
        "elasticloadbalancing:ModifyListener",
        "elasticloadbalancing:DescribeListeners",
        "elasticloadbalancing:CreateRule",
        "elasticloadbalancing:DeleteRule",
        "elasticloadbalancing:ModifyRule",
        "elasticloadbalancing:DescribeRules"
      ],
      "Resource": "*"
    },
    {
      "Sid": "AutoScalingManagement",
      "Effect": "Allow",
      "Action": [
        "autoscaling:CreateAutoScalingGroup",
        "autoscaling:UpdateAutoScalingGroup",
        "autoscaling:DeleteAutoScalingGroup",
        "autoscaling:DescribeAutoScalingGroups",
        "autoscaling:CreateLaunchConfiguration",
        "autoscaling:DescribeLaunchConfigurations",
        "autoscaling:DeleteLaunchConfiguration"
      ],
      "Resource": "*"
    },
    {
      "Sid": "SecurityGroupAccess",
      "Effect": "Allow",
      "Action": [
        "ec2:CreateSecurityGroup",
        "ec2:DescribeSecurityGroups",
        "ec2:AuthorizeSecurityGroupIngress",
        "ec2:AuthorizeSecurityGroupEgress",
        "ec2:RevokeSecurityGroupIngress",
        "ec2:RevokeSecurityGroupEgress",
        "ec2:DeleteSecurityGroup"
      ],
      "Resource": "*"
    },
    {
      "Sid": "CostAllocationTags",
      "Effect": "Allow",
      "Action": [
        "tag:GetResources",
        "tag:GetTagKeys",
        "tag:GetTagValues",
        "billing:GetBillingData",
        "organizations:ListAccounts",
        "account:GetAccountInformation",
        "ce:GetTags",
        "ce:ListCostAllocationTags",
        "ce:UpdateCostAllocationTagsStatus"
      ],
      "Resource": "*"
    }
  ]
}
```

- **GoTo `IAM` -> `User` -> `Harsh` -> `Add permissions` -> `Create inline policy` -> `JSON` and paste harsh.json inside `Policy editor`**

  ![image](https://github.com/user-attachments/assets/744c983d-4775-4671-8ef6-13391fa8c800)

- **Click `Next` -> Give a policy name ex: harsh.json, then click `Create policy`**

  ![image](https://github.com/user-attachments/assets/99c47e57-b90e-41cc-a391-929ac256236d)

- **Policy created ;)**

### 4. Nishkarsh

| Task Description                        | AWS Services Involved           | Required Access Level |
| --------------------------------------- | ------------------------------- | --------------------- |
| Setup Infra for Attendance              | Auto Scaling, Launch Templates  | **Create/manage**     |
| Setup Infra for Frontend                | EC2 Instances                   | **Create/manage**     |
| Setup Network Skeleton for Dev (manual) | VPC, Subnets, IGW, Route Tables | **Full access**       |

> nishkarsh.json

```
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Sid": "AutoScalingAccess",
      "Effect": "Allow",
      "Action": [
        "autoscaling:CreateAutoScalingGroup",
        "autoscaling:UpdateAutoScalingGroup",
        "autoscaling:DeleteAutoScalingGroup",
        "autoscaling:DescribeAutoScalingGroups",
        "autoscaling:CreateLaunchConfiguration",
        "autoscaling:DescribeLaunchConfigurations",
        "autoscaling:DeleteLaunchConfiguration"
      ],
      "Resource": "*"
    },
    {
      "Sid": "EC2InstanceAccess",
      "Effect": "Allow",
      "Action": [
        "ec2:RunInstances",
        "ec2:TerminateInstances",
        "ec2:DescribeInstances",
        "ec2:StartInstances",
        "ec2:StopInstances",
        "ec2:RebootInstances",
        "ec2:CreateTags",
        "ec2:DescribeImages"
      ],
      "Resource": "*"
    },
    {
      "Sid": "NetworkInfrastructureAccess",
      "Effect": "Allow",
      "Action": [
        "ec2:CreateVpc",
        "ec2:DescribeVpcs",
        "ec2:DeleteVpc",
        "ec2:CreateSubnet",
        "ec2:DescribeSubnets",
        "ec2:DeleteSubnet",
        "ec2:CreateInternetGateway",
        "ec2:AttachInternetGateway",
        "ec2:DescribeInternetGateways",
        "ec2:DeleteInternetGateway",
        "ec2:CreateRouteTable",
        "ec2:AssociateRouteTable",
        "ec2:CreateRoute",
        "ec2:DescribeRouteTables",
        "ec2:DeleteRouteTable",
        "ec2:ReplaceRoute",
        "ec2:ReplaceRouteTableAssociation"
      ],
      "Resource": "*"
    }
  ]
}
```

- **GoTo `IAM` -> `User` -> `Nishkarsh` -> `Add permissions` -> `Create inline policy` -> `JSON` and paste nishkarsh.json inside `Policy editor`**

  ![image](https://github.com/user-attachments/assets/d8fe1e36-ed78-47a4-97a8-57f91fe4d735)

- **Click `Next` -> Give a policy name ex: nishkarsh.json, then click `Create policy`**

  ![image](https://github.com/user-attachments/assets/d05afa0a-d71a-469d-9d17-af8596176668)

- **Policy created ;)**

### 5. Prateek

| Task Description                         | AWS Services Involved | Required Access Level |
| ---------------------------------------- | --------------------- | --------------------- |
| Setup Infra for Employee                 | EC2 Instances         | **Create/manage**     |
| Setup Infra for Attendance               | EC2 Instances         | **Create/manage**     |
| Setup Infra for Frontend                 | Security Groups       | **Create/manage**     |
| Setup Network Skeleton for Dev (Subnets) | Subnets               | **Create/manage**     |

> prateek.json

```
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Sid": "EC2InstanceAccess",
      "Effect": "Allow",
      "Action": [
        "ec2:RunInstances",
        "ec2:TerminateInstances",
        "ec2:DescribeInstances",
        "ec2:StartInstances",
        "ec2:StopInstances",
        "ec2:RebootInstances",
        "ec2:CreateTags",
        "ec2:DescribeImages"
      ],
      "Resource": "*"
    },
    {
      "Sid": "SecurityGroupAccess",
      "Effect": "Allow",
      "Action": [
        "ec2:CreateSecurityGroup",
        "ec2:DescribeSecurityGroups",
        "ec2:AuthorizeSecurityGroupIngress",
        "ec2:AuthorizeSecurityGroupEgress",
        "ec2:RevokeSecurityGroupIngress",
        "ec2:RevokeSecurityGroupEgress",
        "ec2:DeleteSecurityGroup"
      ],
      "Resource": "*"
    },
    {
      "Sid": "SubnetManagement",
      "Effect": "Allow",
      "Action": [
        "ec2:CreateSubnet",
        "ec2:DeleteSubnet",
        "ec2:DescribeSubnets",
        "ec2:ModifySubnetAttribute"
      ],
      "Resource": "*"
    }
  ]
}
```

- **GoTo `IAM` -> `User` -> `Prateek` -> `Add permissions` -> `Create inline policy` -> `JSON` and paste prateek.json inside `Policy editor`**

  ![image](https://github.com/user-attachments/assets/f1018850-0548-4a52-8357-aa61b1dd9b1e)

- **Click `Next` -> Give a policy name ex: prateek.json, then click `Create policy`**

  ![image](https://github.com/user-attachments/assets/a3dc15cd-4df3-4e54-984d-34601b78dcc9)

- **Policy created ;)**

### 6. Prince

| Task Description             | AWS Services Involved          | Required Access Level |
| ---------------------------- | ------------------------------ | --------------------- |
| Dev Network Skeleton         | NAT Gateway, IGW, Route Tables | **Create/manage**     |
| Infra for Employee           | Auto Scaling                   | **Create/manage**     |
| Infra for Frontend           | ALB Listener Rules             | **Create/manage**     |
| Infra for Attendance         | Security Groups                | **Create/manage**     |

> prince.json

```
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Sid": "NetworkInfraAccess",
      "Effect": "Allow",
      "Action": [
        "ec2:CreateNatGateway",
        "ec2:DeleteNatGateway",
        "ec2:DescribeNatGateways",
        "ec2:CreateInternetGateway",
        "ec2:AttachInternetGateway",
        "ec2:DeleteInternetGateway",
        "ec2:DescribeInternetGateways",
        "ec2:CreateRouteTable",
        "ec2:AssociateRouteTable",
        "ec2:CreateRoute",
        "ec2:ReplaceRoute",
        "ec2:DeleteRouteTable",
        "ec2:ReplaceRouteTableAssociation",
        "ec2:DescribeRouteTables"
      ],
      "Resource": "*"
    },
    {
      "Sid": "AutoScalingAccess",
      "Effect": "Allow",
      "Action": [
        "autoscaling:CreateAutoScalingGroup",
        "autoscaling:UpdateAutoScalingGroup",
        "autoscaling:DeleteAutoScalingGroup",
        "autoscaling:DescribeAutoScalingGroups",
        "autoscaling:CreateLaunchConfiguration",
        "autoscaling:DescribeLaunchConfigurations",
        "autoscaling:DeleteLaunchConfiguration"
      ],
      "Resource": "*"
    },
    {
      "Sid": "ALBListenerRules",
      "Effect": "Allow",
      "Action": [
        "elasticloadbalancing:CreateListener",
        "elasticloadbalancing:DeleteListener",
        "elasticloadbalancing:ModifyListener",
        "elasticloadbalancing:DescribeListeners",
        "elasticloadbalancing:CreateRule",
        "elasticloadbalancing:DeleteRule",
        "elasticloadbalancing:ModifyRule",
        "elasticloadbalancing:DescribeRules"
      ],
      "Resource": "*"
    },
    {
      "Sid": "SecurityGroupAccess",
      "Effect": "Allow",
      "Action": [
        "ec2:CreateSecurityGroup",
        "ec2:DescribeSecurityGroups",
        "ec2:AuthorizeSecurityGroupIngress",
        "ec2:AuthorizeSecurityGroupEgress",
        "ec2:RevokeSecurityGroupIngress",
        "ec2:RevokeSecurityGroupEgress",
        "ec2:DeleteSecurityGroup"
      ],
      "Resource": "*"
    }
  ]
}
```

- **GoTo `IAM` -> `User` -> `Prince` -> `Add permissions` -> `Create inline policy` -> `JSON` and paste prince.json inside `Policy editor`**

  ![image](https://github.com/user-attachments/assets/b41ae983-3b37-4655-93b6-59afa829be36)

- **Click `Next` -> Give a policy name ex: prince.json, then click `Create policy`**

  ![image](https://github.com/user-attachments/assets/dc8d0afa-3e57-4dac-8cf1-e4adaecc67c2)

- **Policy created ;)**

### Shivani

| Task Description               | AWS Services Involved                                                       | Required Access Level |
| ------------------------------ | --------------------------------------------------------------------------- | --------------------- |
| Setup Network Skeleton for Dev | VPC, NAT Gateway, Internet Gateway (IGW), Route Tables, Network ACLs (NACL) | Create/manage         |
| Infra for Employee             | EC2 Instances, Security Groups, Auto Scaling                                | Create/manage         |
| Infra for Frontend             | Application Load Balancer (ALB), Listener Rules                             | Create/manage         |
| Infra for Attendance           | Security Groups                                                             | Create/manage         |
| Setup syclladb manually        | EC2 Instances                                                               | Create/manage         |
| AWS Service Control Policies   | AWS Organizations Policies                                                  | Create/manage         |

> shivani.json

```
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Action": [
        "ec2:CreateVpc",
        "ec2:DescribeVpcs",
        "ec2:DeleteVpc",
        "ec2:CreateSubnet",
        "ec2:DescribeSubnets",
        "ec2:DeleteSubnet",
        "ec2:CreateNetworkAcl",
        "ec2:DescribeNetworkAcls",
        "ec2:DeleteNetworkAcl",
        "ec2:CreateNetworkAclEntry",
        "ec2:DeleteNetworkAclEntry",
        "ec2:ReplaceNetworkAclEntry"
      ],
      "Resource": "*"
    },
    {
      "Effect": "Allow",
      "Action": [
        "ec2:CreateSecurityGroup",
        "ec2:DescribeSecurityGroups",
        "ec2:AuthorizeSecurityGroupIngress",
        "ec2:AuthorizeSecurityGroupEgress",
        "ec2:RevokeSecurityGroupIngress",
        "ec2:RevokeSecurityGroupEgress",
        "ec2:DeleteSecurityGroup"
      ],
      "Resource": "*"
    },
    {
      "Effect": "Allow",
      "Action": [
        "elasticloadbalancing:CreateListener",
        "elasticloadbalancing:DescribeListeners",
        "elasticloadbalancing:DeleteListener",
        "elasticloadbalancing:ModifyListener",
        "elasticloadbalancing:AddListenerCertificates",
        "elasticloadbalancing:CreateRule",
        "elasticloadbalancing:DescribeRules",
        "elasticloadbalancing:DeleteRule",
        "elasticloadbalancing:ModifyRule"
      ],
      "Resource": "*"
    },
    {
      "Effect": "Allow",
      "Action": [
        "ec2:RunInstances",
        "ec2:DescribeInstances",
        "ec2:StartInstances",
        "ec2:StopInstances",
        "ec2:TerminateInstances",
        "ec2:CreateTags",
        "ec2:DeleteTags"
      ],
      "Resource": "*"
    },
    {
      "Effect": "Allow",
      "Action": [
        "organizations:CreatePolicy",
        "organizations:DescribePolicy",
        "organizations:DeletePolicy",
        "organizations:UpdatePolicy",
        "organizations:ListPolicies",
        "organizations:AttachPolicy",
        "organizations:DetachPolicy"
      ],
      "Resource": "*"
    }
  ]
}
```

- **GoTo `IAM` -> `User` -> `Shivani` -> `Add permissions` -> `Create inline policy` -> `JSON` and paste shivani.json inside `Policy editor`**

  ![image](https://github.com/user-attachments/assets/4d1593ab-c96f-4f98-b2b6-a5e01d83ddf7)

- **Click `Next` -> Give a policy name ex: shivani.json, then click `Create policy`**

  ![image](https://github.com/user-attachments/assets/45c9b38a-3701-4bb4-8ac3-7a5d95d6892c)

- **Policy created ;)**

### 8. Vardaan

| Task Description               | AWS Services Involved                       | Required Access Level |
| ------------------------------ | ------------------------------------------- | --------------------- |
| Setup Network Skeleton for Dev | EC2 (Security Groups)                       | Create/manage         |
| Infra for Employee             | Elastic Load Balancing (ALB Listener Rules) | Create/manage         |
| Setup syclladb manually        | EC2 (Security Groups)                       | Create/manage         |
| Infra for Notification         | Auto Scaling                                | Create/manage         |

> vardaan.json

```
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Action": [
        "ec2:CreateSecurityGroup",
        "ec2:DescribeSecurityGroups",
        "ec2:AuthorizeSecurityGroupIngress",
        "ec2:AuthorizeSecurityGroupEgress",
        "ec2:RevokeSecurityGroupIngress",
        "ec2:RevokeSecurityGroupEgress",
        "ec2:DeleteSecurityGroup"
      ],
      "Resource": "*"
    },
    {
      "Effect": "Allow",
      "Action": [
        "elasticloadbalancing:CreateListener",
        "elasticloadbalancing:DescribeListeners",
        "elasticloadbalancing:DeleteListener",
        "elasticloadbalancing:ModifyListener",
        "elasticloadbalancing:CreateRule",
        "elasticloadbalancing:DescribeRules",
        "elasticloadbalancing:DeleteRule",
        "elasticloadbalancing:ModifyRule"
      ],
      "Resource": "*"
    },
    {
      "Effect": "Allow",
      "Action": [
        "autoscaling:CreateAutoScalingGroup",
        "autoscaling:UpdateAutoScalingGroup",
        "autoscaling:DeleteAutoScalingGroup",
        "autoscaling:DescribeAutoScalingGroups",
        "autoscaling:PutScalingPolicy",
        "autoscaling:DescribePolicies",
        "autoscaling:DeletePolicy"
      ],
      "Resource": "*"
    }
  ]
}
```

- **GoTo `IAM` -> `User` -> `Vardaan` -> `Add permissions` -> `Create inline policy` -> `JSON` and paste vardaan.json inside `Policy editor`**

  ![image](https://github.com/user-attachments/assets/fe565da4-fce6-4d2f-a5e7-807efaf0965e)

- **Click `Next` -> Give a policy name ex: vardaan.json, then click `Create policy`**

  ![image](https://github.com/user-attachments/assets/d231d7c0-d530-4834-b1df-0f56ca0def90)

- **Policy created ;)**

### 9. Yuvraj

| Task Description                        | AWS Services Involved    | Required Access Level  |
| --------------------------------------- | ------------------------ | ---------------------- |
| Setup Infra for Notification            | EC2 Instances            | Create/manage          |
| Setup Network Skeleton for Dev Manually | Load Balancers (ALB/NLB) | Create/manage          |
| Setup Infra for Salary                  | Auto Scaling             | Create/manage          |
| IAM Management                          | IAM                      | Create custom policies |
| Setup PostgreSQL manually               | EC2 Instances            | Create/manage          |

> yuvraj.json

```
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Action": [
        "ec2:RunInstances",
        "ec2:DescribeInstances",
        "ec2:StartInstances",
        "ec2:StopInstances",
        "ec2:TerminateInstances",
        "ec2:CreateTags",
        "ec2:DeleteTags"
      ],
      "Resource": "*"
    },
    {
      "Effect": "Allow",
      "Action": [
        "elasticloadbalancing:CreateLoadBalancer",
        "elasticloadbalancing:DeleteLoadBalancer",
        "elasticloadbalancing:DescribeLoadBalancers",
        "elasticloadbalancing:CreateTargetGroup",
        "elasticloadbalancing:DeleteTargetGroup",
        "elasticloadbalancing:ModifyLoadBalancerAttributes",
        "elasticloadbalancing:DescribeTargetGroups"
      ],
      "Resource": "*"
    },
    {
      "Effect": "Allow",
      "Action": [
        "autoscaling:CreateAutoScalingGroup",
        "autoscaling:UpdateAutoScalingGroup",
        "autoscaling:DeleteAutoScalingGroup",
        "autoscaling:DescribeAutoScalingGroups",
        "autoscaling:PutScalingPolicy",
        "autoscaling:DescribePolicies",
        "autoscaling:DeletePolicy"
      ],
      "Resource": "*"
    },
    {
      "Effect": "Allow",
      "Action": [
        "iam:CreatePolicy",
        "iam:ListPolicies",
        "iam:DeletePolicy",
        "iam:GetPolicy",
        "iam:GetPolicyVersion"
      ],
      "Resource": "*"
    }
  ]
}
```

- **GoTo `IAM` -> `User` -> `Yuvraj` -> `Add permissions` -> `Create inline policy` -> `JSON` and paste yuraj.json inside `Policy editor`**

  ![image](https://github.com/user-attachments/assets/89777187-cc9a-4718-8407-68222844eac7)

- **Click `Next` -> Give a policy name ex: yuvraj.json, then click `Create policy`**

  ![image](https://github.com/user-attachments/assets/e0e63048-8e9c-41ae-a117-51c6870443a8)

- **Policy created ;)**



# EOF
