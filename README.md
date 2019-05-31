# Test



## 索引部分
---

> 目录结构

- searchengine
    - common
        - article
            - ArticleJsonParser:根据文章内容json和关键词json，分别建立文章索引与往hbase中存入文章。
        - bean
            - BeanDoc: 文章Bean
        - query
            - segment 分词
                - ChineseSegmentation : 查询的中文分词依赖
            - synonym 近义词，同义词
                - Synonym 近义词查询
                    - PinyinUtil ：生成拼音
                    - SpellChecker ：拼写检查的实现
                    - SoundexCoder : 生成Soundex码，建立拼音码
            - spell 拼写
        - utils
            - RedisUtil
            - HbaseUtil
            - QueryUtil
        - vec

---


> 功能使用

### 1.建立索引

    通过ArticleJsonParser生成

- 需要修改文章json信息和关键词json信息的目录

### 2.查询
    通过 List<String> query(String queryString) 返回查询内容文章的ID

- 注:依赖Synonym对象（模型的载入），RedisUtil（redis的连接），HBaseUtil（HBase的连接），提前注入模型对象再查询的时候能获取更好的查询体验。

### 3.拼写检查

    通过List<String> checkSpell(String token)方法检查中文，返回找到索引中存在的拼写相似，读音相近的词语。
- 注:依赖于RedisUtil

### 4.分页检索

    当完成查询，需要将内容分页显示时，调用 RedisUtil中的public Set<Tuple> resultPaging(int currentPage,int  count)

- currentPage:当前页面
- count:一个页面需要显示的内容数量
- 返回: 文章ID和对应score的元组set，可以通过遍历getElement获取文章ID

### 5.根据文章ID检索文章
- 通过HbaseUtil 的BeanDoc getDataByRowKey(String rowKey)方法
    - 传入文章ID
    - 返回BeanDoc对象
- 注：依赖于HbaseUtil



### TOLIST

- [ ] return note item number for page
- [ ] recommend
    - recommend tool
    - based on user graph
- [ ] note digest ang highlight
- [ ] index page background unify
- [ ] dynamic update search index 
- [ ] main page redesign