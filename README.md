# 以dcm4che取出Dicom檔內的圖片及資訊


## 使用dcm4che取出Dicom檔內的圖片及資訊(Attribute)

### 一、建立dcm4che開發環境

先在網路上搜尋dcm4che 5.22.4安裝包下載



我的開發環境是windows x86系統，如果需要在開發時使用dcm4che的opencv函式庫時需要將opencv_java.dll加入JDK的bin目錄下。

步驟如下:
1. 將下載的dcm4che 5.22.4安裝包解壓縮後進入lib目錄

![image](https://github.com/YICHENGSYU/dicomParser/assets/107453333/47850bcd-cc85-42c5-a9bb-b024448eceab)

2. 選擇win-x86_64資料夾

![image](https://github.com/YICHENGSYU/dicomParser/assets/107453333/0a3fa685-c86e-4e00-b5e4-d97363ee10b4)

3. 複製opencv_java.dll

![image](https://github.com/YICHENGSYU/dicomParser/assets/107453333/4e675b82-33df-4b62-9c1c-17756d777f42)

4. 進入開發java程式使用的jdk目錄下，進入bin資料夾

![image](https://github.com/YICHENGSYU/dicomParser/assets/107453333/10e6bc55-b11f-47dd-8ef2-80dfc5acac09)


5. 將opencv_java.dll貼上 

![image](https://github.com/YICHENGSYU/dicomParser/assets/107453333/da09348c-568d-4f93-9e73-55db4466f15a)



6. 開始進入愉快的程式開發環節 !

**註 : .dll檔案為Dynamic-Linking Library，為動態鏈結函式庫，與靜態鏈結函式庫(Static-linking Lib)不同，使用動態鏈結函式庫時，主程式是連接至動態鏈結函式庫後使用動態鏈結函式庫內的函式，動態鏈結函式庫會有自己的memory。Windows系統的動態鏈結函式庫副檔名為.dll 。**



### 二、進行Java Application開發(Springboot Web Applicaion)

於pom.xml添加以下的dependency


```java=11
        <dependency>
            <groupId>org.apache.axis</groupId>
            <artifactId>axis</artifactId>
            <version>1.4</version>
        </dependency>

        <!-- https://mvnrepository.com/artifact/dcm4che/dcm4che -->
        <dependency>
            <groupId>dcm4che</groupId>
            <artifactId>dcm4che</artifactId>
            <version>2.0.29</version>
            <type>pom</type>
        </dependency>


        <!-- https://mvnrepository.com/artifact/org.dcm4che/dcm4che-core -->
        <dependency>
            <groupId>org.dcm4che</groupId>
            <artifactId>dcm4che-core</artifactId>
            <version>5.22.4</version>
            <exclusions>
                <exclusion>
                    <artifactId>slf4j-log4j12</artifactId>
                    <groupId>org.slf4j</groupId>
                </exclusion>
            </exclusions>
        </dependency>

        <!-- https://mvnrepository.com/artifact/org.dcm4che/dcm4che-imageio -->
        <dependency>
            <groupId>org.dcm4che</groupId>
            <artifactId>dcm4che-imageio</artifactId>
            <version>5.22.4</version>
        </dependency>

        <!-- https://mvnrepository.com/artifact/org.dcm4che.tool/dcm4che-tool-common -->
        <dependency>
            <groupId>org.dcm4che.tool</groupId>
            <artifactId>dcm4che-tool-common</artifactId>
            <version>5.22.4</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/commons-cli/commons-cli -->
        <dependency>
            <groupId>commons-cli</groupId>
            <artifactId>commons-cli</artifactId>
            <version>1.4</version>
        </dependency>

        <dependency>
            <groupId>org.dcm4che</groupId>
            <artifactId>dcm4che-image</artifactId>
            <version>5.22.4</version>
            <exclusions>
                <exclusion>
                    <artifactId>slf4j-log4j12</artifactId>
                    <groupId>org.slf4j</groupId>
                </exclusion>
            </exclusions>
        </dependency>

        <dependency>
            <groupId>org.dcm4che</groupId>
            <artifactId>dcm4che-imageio-opencv</artifactId>
            <version>5.22.4</version>
        </dependency>

        <dependency>
            <groupId>org.dcm4che</groupId>
            <artifactId>dcm4che-imageio-rle</artifactId>
            <version>5.22.4</version>
            <scope>runtime</scope>
        </dependency>


        <!-- https://mvnrepository.com/artifact/org.dcm4che/dcm4che-json -->
        <dependency>
            <groupId>org.dcm4che</groupId>
            <artifactId>dcm4che-json</artifactId>
            <version>5.22.4</version>
        </dependency>


```

根據需求使用dcm4che開發程式。

### 三、範例程式說明

我寫了一隻簡單的程式來取出多幀或單幀(multi-frame or single-frame)的dicom檔內的圖片。

讀取本地的dicom檔，回傳PNG圖片的Base64字串。
http://localhost:8112/dicomToPngParser/dicomToPng

![image](https://github.com/YICHENGSYU/dicomParser/assets/107453333/54d84176-8d8e-4653-9176-bcbbff2e1e75)

imgNumber參數為要取出來第幾張圖片，imgNumber=2 代表要取出dicom檔的第二張圖片。

上傳dicom檔後，回傳PNG圖片的Base64字串。
http://localhost:8112/dicomToPngParser/dicomToPng

![image](https://github.com/YICHENGSYU/dicomParser/assets/107453333/0267238f-964e-46d6-880f-9b8eb2438f8c)

imgNumber參數為要取出來第幾張圖片，imgNumber=5 代表要取出dicom檔的第五張圖片。



### 四、Util工具類功能說明

Util工具類裡有兩個function，一個是**getDicomPngBase64**，另一個是**getDicomAttribute**。

- getDicomPngBase64 : 輸入File物件(dicom檔)後回傳字串陣列(dicom檔內所有圖片的Base64字串)。
- getDicomAttribute : 輸入File物件(dicom檔)後回傳字串(dicom檔內的資訊，包含Patient name、dicom檔的楨數等等，可以使用attribute的Tag篩選出需要的資訊)

### 五、將使用dcm4che套件開發的SpringBooot Web Application容器化(Dockerize)

在開發時是藉由將opencv_java.dll加入JDK的bin目錄下使用opencv-java函式庫，但在將SpringBooot Web Application容器化(Dockerize)時的base image會傾向使用linux系統，更輕量且開源。所以需要使用libopencv_java.so，並將SpringBooot Web Application動態鏈結到libopencv_java.so函式庫。

可以先建立一個動態鏈結libopencv_java.so的JDK，之後在開發需要使用有動態鏈結libopencv_java.so到JDK的Application時，可以直接使用此JDK。

```dockerfile=
FROM ubuntu:18.04

RUN apt-get update
RUN apt-get install -y ant
RUN apt-get install -y openjdk-11-jdk
ARG JAVA_HOME="/usr/lib/jvm/java-11-openjdk-amd64"
ENV JAVA_HOME="/usr/lib/jvm/java-11-openjdk-amd64"
RUN echo $JAVA_HOME
RUN java -version
ADD ./libopencv_java.so /usr/lib/libopencv_java.so

```

**建立 動態鏈結libopencv_java.so的JDK 的image**
```
docker build -t openjdk11withopencv .
```


接下來要將SpringBooot Web Application容器化(Dockerize)

如果是使用intellij idea進行開發，可以點開右邊的maven，按下install，建立jar檔。

![image](https://github.com/YICHENGSYU/dicomParser/assets/107453333/5559d28a-08ee-4590-a65d-49a65b3496e9)

建立成功後開始編寫Dockerfile

![image](https://github.com/YICHENGSYU/dicomParser/assets/107453333/8014a570-130a-43d2-9f50-4dfbad182149)



**Dockerfile**

```dockerfile=
# 以動態鏈結libopencv_java.so的JDK為base image
FROM openjdk11withopencv
COPY ./target/*.jar /Documents/mydocker/demo.jar
WORKDIR /Documents/mydocker
RUN sh -c 'touch demo.jar'
ENTRYPOINT ["java","-jar","demo.jar"]

```


**建立範例SpringBooot Web Application的image**
```
docker build -t dicomparser .
```

**執行範例SpringBooot Web Application的container**
```
docker run -d --name dicomparser -p 8112:8112 ${dicomparser的imageID}
```

### 六、將SpringBooot Web Application deploy到K8S上

將image打成.tar檔後傳送到要部署的VM上，執行deployment。


**將image打成.tar檔**

```
docker save -o dicomparser.tar dicomparser
```


**還原image到Docker**
```
docker load -i dicomparser.tar
```

**K8S1.27.1版本還原image到containerD**
```
sudo ctr --namespace=k8s.io images import dicomparser.tar
```

**建立kube-dicomparser-deployment.yaml**
```
vim kube-dicomparser-deployment.yaml
```

```yaml=
---
apiVersion: apps/v1
kind: Deployment
metadata:
  name: dicomparser
  namespace: default
  labels:
    app: dicomparser
spec:
  selector:
    matchLabels:
      app: dicomparser
  strategy:
    type: Recreate
  replicas: 1
  template:
    metadata:
      name: dicomparser
      labels:
        app: dicomparser
    spec:
      nodeName: k8s-node2
      containers:
        - name: dicomparser
          image: dicomparser
          imagePullPolicy: IfNotPresent
          ports:
            - containerPort: 8112
          command: ["java","-jar","demo.jar"]

---
apiVersion: v1
kind: Service
metadata:
  name: dicomparser
  namespace: default
  labels:
    app: dicomparser
spec:
  type: NodePort
  selector:
    app: dicomparser
  ports:
    - name: http
      port: 8112
      targetPort: 8112
      protocol: TCP
      nodePort: 38112


```

**進行K8S deployment**
```
kubectl apply -f kube-dicomparser-deployment.yaml
```
