#!/usr/bin/env bash
baseDir=`pwd`
#mvn clean package -DskipTests

finalName=lemon-server-0.0.1

if [ -e ${finalName} ]
then
echo "文件夹已存在,删除重新打包."
rm -rf ${finalName}
else
echo "创建文件夹:${finalName}"
fi
mkdir ${baseDir}/${finalName}
cp ${baseDir}/target/lemon-server.jar ${baseDir}/release/docker/jar
cp -r ${baseDir}/release/* ${baseDir}/${finalName}/
tar czvf lemon-server-0.0.1.tar.gz ${finalName}
rm -rf lemon-server-0.0.1
