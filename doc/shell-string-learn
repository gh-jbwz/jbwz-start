# Shell的String 用方法

1. 两种截取字符串用法及for循环获取目录文件名方法
```shell
#!/bin/bash
set -e
bdir=`pwd`
startScript="start-lemon-server.sh"
appDir=""
logPrefix=">#: "
# 执行shell命令可以使用$()
files=$(ls $bdir)
for filename in $files
do
# 获取文件名字后缀"${filename:0-6}":从字符串有最右边数,截取从第6位开始到最右边所有字符
if [ "${filename:0-6}" == "tar.gz" ]
then
echo "${logPrefix} tar xzf ${filename}"
tar xzf ${filename}
# 获取文件价名称 "${filename%.tar.gz}"从字符串最右边开始找第一个字符(.tar.gz),然后截取当前字符左边的所有字符
appDir=${filename%.tar.gz}
echo "${logPrefix} cd  ${appDir}/bin"
cd ${appDir}/bin
pwd
echo "${logPrefix} start-app"
./${startScript}
fi
done

```
