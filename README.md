# README for Calculator

This a simple terminal version of the calculator. 
## Build Image

To build the docker image clone the repo and follow the comamnds below
Make your changes
```bash
    mvn clean install
```

```bash
    docker build -t <tagname>
    docker run -e MODE="<mode>" -it <tagname>
```

## Direct Usage

To use the docker image directly follow the below commands 

```bash
    docker pull byterider/cal:<tagnumber>
    docker run -e MODE="<mode>" -it byterider/cal:<tagnumber>
    
```

## Arguments and Environments
_tagnumber_: tag number of the docker image can be used. For latest tagnumber check the [registry](https://hub.docker.com/r/byterider/cal) .<br>
_mode_: This is basically the environment: "DEV", "PROD".

For **"DEVE"** environment the docker image will automatically take the input from the input.txt and runthe app. (No need of manual intervention)<br>
Hence we can use 
```bash 
    docker run -e MODE="DEVE" byterider/cal:<tagnumber>
```
For **"PROD"** environment the docker image is interactive. 
Here it changes as 
```bash 
    docker run -e MODE="PROD" -it byterider/cal:<tagnumber>
```
