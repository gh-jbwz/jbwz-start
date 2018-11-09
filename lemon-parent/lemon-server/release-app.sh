#!/usr/bin/env bash
baseDir=`pwd`
appName=lemon-server
appVersion=0.0.1
appFullName=${appName}-${appVersion}
sed -i 's/^ENV appName=.*/ENV appName='${appFullName}'/' ${baseDir}/release/docker/Dockerfile

mvn clean package -DskipTests
if [ -e ${appFullName} ]
then
echo "�ļ����Ѵ���,ɾ�����´��."
rm -rf ${appFullName}
fi
echo "��ʼ�����Ŀ"
mkdir ${baseDir}/${appFullName}
cp ${baseDir}/target/${appFullName}.jar ${baseDir}/release/docker/jar
cp -r ${baseDir}/release/* ${baseDir}/${appFullName}/

tar czvf ${appFullName}.tar.gz ${appFullName}
echo "������:${baseDir}/${appFullName}.tar.gz"
rm -rf ${appFullName}
