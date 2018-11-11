# 数据库命名规范
> 说明：指数据库对象如表（TABLE）、序列（SEQUENCE）、过程（PROCEDURE）、触发器（TRIGGER）等的命名约定。
### 基本命名原则
- 使用有意义的英文词汇，词汇中间以下划线(_)分隔。（不要用拼音）
- 只能使用英文字母，数字，下划线，并以英文字母开头。
- 库、表、字段全部采用小写，不要使用驼峰式命名。
- 避免用ORACLE、MySQL的保留字，如desc，关键字如index。
- 命名禁止超过32个字符，须见名之意，建议使用名词不是动词

#### 表命名规范
- 同一个模块的表尽可能使用相同的前缀，表名称尽可能表达含义。所有日志表均以 log_ 开头
- 长度不超过25个字符。

#### 字命名规范
- 使用表达其实际含义的英文单词或简写。
   布尔意义的字段以“is_”作为前缀，后接动词过去分词。
- 各表之间相同意义的字段,名字应该相同.
-  各表之间相同意义的字段,以去掉表名模块的前缀用 表名缩写_字段名 命名。
   
   
#### 表设计规范   
- 禁止使用外键。
- 默认使用utf8mb4字符集，数据库排序规则使用utf8mb4_general_ci，（由于数据库定义使用了默认，数据表可以不再定义，但为保险起见，建议都写上）。

- 表中所有主键名字都设置为`id` 类型为`int`,且为自动增长
- 如无说明，表必须包含create_time和modify_time字段，即表中必须包含记录创建时间和修改时间的字段
- 如无说明，表必须包含`is_del`，用来标示数据是否被删除，原则上数据库数据不允许物理删除。
- 排序字段用`order_id`,且在程序中默认使用升序排列；
- 如无备注，所有的布尔值字段，如is_hot、is_del，都必须设置一个默认值，并设为0；
- 如无备注，所有字段都设置NOT NULL，并设置默认值；
- 存储时间（精确到秒）建议使用TIMESTAMP类型，因为TIMESTAMP使用4字节，DATETIME使用8个字节。
- 存储日期使用DATE类型。

- 建议使用INT UNSIGNED存储IPV4。
- 尽量不使用TEXT、BLOB类型
- 存储精确浮点数必须使用DECIMAL替代FLOAT和DOUBLE
- 时间字段，除特殊情况一律采用int来记录unix_timestamp
- 禁止在数据库中使用VARBINARY、BLOB存储图片、文件等。建议使用其他方式存储（TFS/SFS），MySQL只保存指针信息。
- 禁止单条记录大小超过8k（列长度(中文)*3(UTF8)-列长度(英文)*1）