# SpringBoot Blocking and Reactive Examples

SpringBoot with JPA/Hibernate, JDBC and R2DBC examples.

All the applications are tested with 256MB memory/2 CPU cores and 128MB memory/1 CPU core.

```
docker run -it --rm --memory="256MB" --cpus="2.0" -p 8080:8080 --name spring-rest-data -e "DATABASE=jdbc:postgresql://$HOST_IP:5432/postgres" spring-rest-data
docker run -it --rm --memory="128MB" --cpus="1.0" -p 8080:8080 --name spring-rest-data -e "DATABASE=jdbc:postgresql://$HOST_IP:5432/postgres" spring-rest-data
```

Apache `ab` is used for the load tests.

```
# such an example is this
for i in {1..10}; do ab -n 500 -c 20 "http://centos:8080/city" ; sleep 1; done
```

The following are the results for the `ab` tests.


|      | JPA Repository (ms) Entity 128MB/1C | JPA Repository (ms) DTO 128MB/1C | JPA Repository (ms) Entity 256MB/2C | JPA Repository (ms) DTO 256MB/2C | JDBC (ms) Model 128MB/1C | JDBC (ms) Model 128MB/1C | R2DBC (ms) Entity 128MB/1C | R2DBC (ms) DTO 128MB/1C | R2DBC (ms) Entity 256MB/2C | R2DBC (ms) DTO 256MB/2C | R2DBC (ms) Model - NO HIBERNATE 128MB/1C | R2DBC (ms) Model - NO HIBERNATE 256MB/2C | Go REST 128MB/1C | Go REST 256MB/2C |
| ---- | ----------------------------------- | -------------------------------- | ----------------------------------- | -------------------------------- | ------------------------ | ------------------------ | -------------------------- | ----------------------- | -------------------------- | ----------------------- | ---------------------------------------- | ---------------------------------------- | ---------------- | ---------------- |
| 50%  | 6163.333333                         | 6163.333333                      | 342.2                               | 331.8                            | 1028.6                   | 142.4                    | 4770                       | 4770                    | 493                        | 519.8                   | 332                                      | 334.2                                    | 1828             | 146.6            |
| 66%  | 6985.333333                         | 6985.333333                      | 377.2                               | 375.6                            | 1303.2                   | 189.2                    | 5419.4                     | 5419.4                  | 570.6                      | 619.8                   | 344                                      | 347.4                                    | 2157.8           | 180.6            |
| 75%  | 7801.333333                         | 7801.333333                      | 392.6                               | 393.8                            | 1464.6                   | 214.6                    | 5930.8                     | 5930.8                  | 620.2                      | 691.6                   | 355                                      | 359.2                                    | 2352.4           | 204.2            |
| 80%  | 8338.666667                         | 8338.666667                      | 404.4                               | 405.2                            | 1584.6                   | 233                      | 6283.4                     | 6283.4                  | 657.4                      | 737.6                   | 362.4                                    | 366                                      | 2455.2           | 219.6            |
| 90%  | 9474                                | 9474                             | 450.2                               | 453.2                            | 1868.2                   | 286.2                    | 7026                       | 7026                    | 773.2                      | 874                     | 377                                      | 379.4                                    | 2791             | 262.8            |
| 95%  | 10891                               | 10891                            | 492.6                               | 492                              | 2140.6                   | 326                      | 7661.6                     | 7661.6                  | 894.2                      | 986.2                   | 555.2                                    | 390                                      | 3073.6           | 306.6            |
| 98%  | 12426.66667                         | 12426.66667                      | 562.2                               | 564.4                            | 2544.8                   | 366                      | 8611.8                     | 8611.8                  | 1024.4                     | 1121.2                  | 812.8                                    | 403.8                                    | 3503             | 357.2            |
| 99%  | 12927.66667                         | 12927.66667                      | 604.4                               | 613.8                            | 2692.4                   | 426                      | 9039                       | 9039                    | 1126.8                     | 1200.8                  | 834.4                                    | 413.8                                    | 3803.8           | 392.4            |
| 100% | 14714.66667                         | 14714.66667                      | 671.8                               | 681.4                            | 3629.4                   | 549.8                    | 10134.8                    | 10134.8                 | 1288                       | 1391.6                  | 850.8                                    | 437                                      | 4450.4           | 469.8            |

![128MB 1 Core](img/128mb1c.png "128MB 1 Core")

![256MB 2 Cores](img/256mb2c.png "256MB 2 Cores")

