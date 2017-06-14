// Step 1.0 启动 MySQL container  
```
docker-compose up  
```
// Step 1.1 验证 MySQL container 正常运行  
```
liyikun$ docker ps  
CONTAINER ID  a7eba683a344
liyikun$ docker exec -it a7eba683a344 /bin/bash  
bash-4.2# mysql -uliyikun -p123456
mysql> show databases  
+--------------------+  
| Database           |  
+--------------------+  
| information_schema |  
| running            |  
+--------------------+  
```
// Step 2.0 编译打包  
```
mvn clean install  
```
// Step 3.0 运行 Application  
```
java -jar target/running-information-analysis-service-1.0.0.RELASE-SNAPSHOT.jar
```
// Step 3.1 检验数据库  
```
mysql> use running  
mysql> show tables;  
+-------------------+  
| Tables_in_running |  
+-------------------+  
| running_info      |    
| user_infoes       |  
+-------------------+  
```
// Step 4.0 打开Postman导入json数据
URL输入 http://localhost:8080/running/update  
选择Body ——> raw ——> JSON(application/json)  
把文档里的json串粘进去，点 Send  
// Step 4.1 检验数据库  
```
mysql> select * from running_info;
+----+------------+------------------+-------------------+------------------+--------------------------------------+---------------------+--------------------+--------------+  
| id | heart_rate | latitude         | longitude         | running_distance | running_id                           | timestamp           | total_running_time | user_info_id |    
+----+------------+------------------+-------------------+------------------+--------------------------------------+---------------------+--------------------+--------------+  
| 78 |         76 |       38.9093216 |       -77.0036435 |            39492 | 7c08973d-bed4-4cbd-9c28-9282a02a6032 | 2017-04-02 02:50:35 |            2139.25 |            1 |  
| 79 |         88 |        39.927434 |        -76.635816 |             1235 | 07e8db69-99f2-4fe2-b65a-52fbbdf8c32c | 2017-04-02 02:50:35 |            3011.23 |            2 |  
| 80 |         87 |        40.083824 |        -76.098019 |            23567 | 2f3c321b-d239-43d6-8fe0-c035ecdff232 | 2017-04-02 02:50:35 |           85431.23 |            3 |  
| 81 |         62 |        42.957466 |        -76.344201 |            11135 | 28810a26-25e6-4680-8baf-59bb07c4aee0 | 2017-04-02 02:50:35 |              98965 |            4 |  
| 82 |        191 |       38.5783821 |       -77.3242436 |              231 | fb0b4725-ac25-4812-b425-d43a18c958bb | 2017-04-02 02:50:35 |                123 |            5 |  
| 83 |        145 |        42.375786 |        -76.870872 |                0 | 35be446c-9ed1-4e3c-a400-ee59bd0b6872 | 2017-04-02 02:50:35 |                  0 |            6 |  
| 84 |        188 | 40.2230391039276 | -76.0626631118454 |              0.1 | 15dfe2b9-e097-4899-bcb2-e0e8e72416ad | 2017-04-02 02:50:35 |                0.1 |            7 |  
+----+------------+------------------+-------------------+------------------+--------------------------------------+---------------------+--------------------+--------------+  
mysql> select * from user_infoes;  
+----+----------------------------------------+----------+  
| id | address                                | username |  
+----+----------------------------------------+----------+  
|  1 | 504 CS Street, Mountain View, CA 88888 | ross0    |  
|  2 | 504 CS Street, Mountain View, CA 88888 | ross1    |  
|  3 | 504 CS Street, Mountain View, CA 88888 | ross2    |  
|  4 | 504 CS Street, Mountain View, CA 88888 | ross3    |  
|  5 | 504 CS Street, Mountain View, CA 88888 | ross4    |  
|  6 | 504 CS Street, Mountain View, CA 88888 | ross5    |  
|  7 | 504 CS Street, Mountain View, CA 88888 | ross6    |  
+----+----------------------------------------+----------+  
```
// Step 5.0 接口检验
打开浏览器，输入
http://localhost:8080/running/search?page=0
输出第一页数据：
```
[  
{  
    runningId: "35be446c-9ed1-4e3c-a400-ee59bd0b6872",  
    totalRunningTime: 0,  
    heartRate: 145,  
    userId: 6,  
    userName: "ross5",  
    userAddress: "504 CS Street, Mountain View, CA 88888",  
    healthWarningLevel: "HIGH"  
},  
{  
    runningId: "07e8db69-99f2-4fe2-b65a-52fbbdf8c32c",  
    totalRunningTime: 3011.23,  
    heartRate: 88,  
    userId: 2,  
    userName: "ross1",  
    userAddress: "504 CS Street, Mountain View, CA 88888",  
    healthWarningLevel: "NORMAL"  
}  
]
```
也可以指定每一页的数量：
http://localhost:8080/running/search?page=0&size=2
通过以下API可以根据running id删除某条信息：  
http://localhost:8080/running/delete?id=7c08973d-bed4-4cbd-9c28-9282a02a6032
