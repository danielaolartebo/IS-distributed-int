# ***Distributed Integration***

## Objective of the project

The main objective of this project is to develop a software system that allows users to approximate definite integrals using different numerical methods in an efficient and distributed manner. The system must be capable of:

1. **Interpret and process functions:** The system must have a "client" that can receive the function to be integrated, along with the integration ranges, through a friendly user interface. This client can implement a mathematical expression interpreter or use an external library to process the function robustly.

2. **Approximate definite integrals:** The core of the system, called "dist_integ", must implement an integral approximation strategy that combines various design patterns and distributed computing techniques. This strategy must seek a balance between the main factors that affect the performance and accuracy of the approximation, such as the calculation speed, the amount of memory used, and the accuracy of the result.

3. **Scalability and efficiency:** The system must be scalable to different hardware configurations, allowing it to run on a single machine or distributed across multiple nodes. The computation distribution should significantly improve overall system performance, especially for complex problems that require large amounts of computational resources.


In summary, this project seeks to develop a robust, efficient and scalable software system for the approximation of definite integrals, using distributed computing techniques and performance analysis to optimize its performance. The system must be easy to use, adaptable to different hardware configurations and provide accurate results efficiently.*

## Design patterns implemented
 - *Master-Worker*
 - *Thread Pool*

## ***Built With*** 🛠️

<p align="left">
    <a href="https://code.visualstudio.com/" target="_blank"> <img src="https://raw.githubusercontent.com/devicons/devicon/2ae2a900d2f041da66e950e4d48052658d850630/icons/vscode/vscode-original.svg" height="60" width = "60"></a>
    <a href="https://code.visualstudio.com/](https://gradle.org/install/)" target="_blank"> <img src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/gradle/gradle-original.svg" height="60" width = "60"></a>
    <a href="https://code.visualstudio.com/](https://gradle.org/install/)" target="_blank"> <img src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/java/java-original.svg" height="60" width = "60"></a>
</p>

## ***Versioned*** 📌

<p align="left">
     <a href="https://git-scm.com/" target="_blank"> <img src="https://raw.githubusercontent.com/devicons/devicon/2ae2a900d2f041da66e950e4d48052658d850630/icons/git/git-original.svg" height="60" width = "60"></a>
    <a href="https://github.com/" target="_blank"> <img src="https://raw.githubusercontent.com/devicons/devicon/2ae2a900d2f041da66e950e4d48052658d850630/icons/github/github-original.svg" height="60" width = "60"></a>
</p>

*For all the updates of the software, check the releases [here](https://github.com/danielaolartebo/PI1-RBE/tags).*

## ***Local Deploy*** 📦

*To run the web app you should follow these steps:*

1️⃣ *Clone or download the repository*

2️⃣ *Open it on your IDE (must have Java 11 and Gradle 6.6 or 7.0 installed)*

3️⃣  *Run the project on the IDE*

## ***Detailed Instructions*** 📦

1. Enter via SSH protocol to machine 11, 15 and 16 (hgrid11, hgrid15, hgrid16).
```bash
ssh swarch@hgrid11
ssh swarch@hgrid15
ssh swarch@hgrid16
```
    
3. Enter the Paz-Olarte directory on each of the machines.

4. Get to the helloworld directory and allow permission to the directory.
```bash
cd /Users/danielaolarte/Downloads/talker-callback-kbd-SebastianPaz-DanielaOlarte/talker-ciclo-kbd-SebastianPaz-DanielaOlarte/helloworld-ret

chmod +x gradlew
```

6. Build the project from the source code, from the server machine.
```bash
./gradlew build
```
    
8. On one of the machines selected in step 1, connect to the server.
```bash
java -jar server/build/libs/server.jar
```
    
10. Build the project from the source code, from the clients' machine.
```bash
./gradlew build
```

12. On the remaining two machines, connect to the client.
```bash
java -jar client/build/libs/client.jar
```


## ***Other commands*** 📦

- Transfer files (fuente-destino)
```bash
scp swarch@xhgrid16:nameofdirectory swarch@xhgrid11:nameofdirectory
swarch@xhgrid11: scp ./ejem.txt swarch@xhgrid16:train
```
- Create files in a directory
```bash
touch example.txt
touch example.zip
```
- Connect to a machine
```bash
ssh swarch@xhgrid11
```
- Change to Java 11
```bash
export JAVA_HOME=$(/usr/libexec/java_home -v11)
```
- Build with bash
```bash
bash ./gradlew build
```

- Update config
```bash
slice2java nombredearchivo
```

- Zip folder
```bash
zip -r my_directory.zip my_directory
```

- Unzip folder
```bash
unzip my_directory.zip
```

## ***Authors*** ✒️

<p align="left">
  <a href="https://github.com/danielaolartebo" target="_blank"> <img src="https://images.weserv.nl/?url=avatars.githubusercontent.com/u/53228651?v=4&h=60&w=60&fit=cover&mask=circle"</a>
  <a href="https://github.com/Sebas-gifPaz777" target="_blank"> <img src="https://images.weserv.nl/?url=avatars.githubusercontent.com/u/84254040?v=4&h=60&w=60&fit=cover&mask=circle"</a>
  <a href="https://github.com/Jun-266" target="_blank"> <img src="https://images.weserv.nl/?url=avatars.githubusercontent.com/u/78438087?v=4&h=60&w=60&fit=cover&mask=circle"</a>
  <a href="https://github.com/Danna-Lopez-M" target="_blank"> <img src="https://images.weserv.nl/?url=avatars.githubusercontent.com/u/112584246?v=4&h=60&w=60&fit=cover&mask=circle"</a>
</p>
---


[![forthebadge](https://forthebadge.com/images/badges/built-with-love.svg)](https://forthebadge.com)
[![forthebadge](https://forthebadge.com/images/badges/for-you.svg)](https://forthebadge.com)
