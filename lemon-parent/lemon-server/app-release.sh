#!/usr/bin/env bash
baseDir=`pwd`
appName=lemon-server
appVersion=0.0.1
appFullName=${appName}-${appVersion}
sed -i 's/^ENV appName=.*/ENV appName='${appFullName}'/' ${baseDir}/release/docker/Dockerfile

set -e
mvn clean package -DskipTests
if [ -e ${appFullName} ]
then
echo "文件夹已存在,删除重新打包."
rm -rf ${appFullName}
fi
echo "开始打包项目"
mkdir ${baseDir}/${appFullName}
cp ${baseDir}/target/${appFullName}.jar ${baseDir}/release/docker/jar/
cp -r ${baseDir}/release/* ${baseDir}/${appFullName}/

tar czvf ${appFullName}.tar.gz ${appFullName}
echo "打包完成:${baseDir}/${appFullName}.tar.gz"
rm -rf ${appFullName}


