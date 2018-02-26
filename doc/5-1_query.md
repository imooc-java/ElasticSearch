# 5-1 query

## 高级查询

* 子条件查询

  特定字段查询所指特定值

* 复合条件查询

  以一定的逻辑组合子条件查询

## 子条件查询

* Query context
* Filter context

### Query context

在查询过程中，除了判断文档是否满足查询条件外，ES还会计算一个 `_score` 来标识匹配的程度，旨在判断目标文档和查询条件匹配的**有多好**。

* 全文本查询 针对文本类型数据
* 字段级别查询 针对结构化数据，如数字、日期等

## 查询示例

POST http://127.0.0.1:9200/book/_search

### match

title 匹配 "ElasticSearch" 或 "入门"

```json
{
    "query": {
        "match": {
            "title": "ElasticSearch入门"
        }
    }
}
```

### match_phrase

title 精确匹配 "ElasticSearch入门"

```json
{
    "query": {
        "match_phrase": {
            "title": "ElasticSearch入门"
        }
    }
}
```

### multi_match

author 或 title 匹配 "瓦力"

```json
{
    "query": {
        "multi_match": {
            "query": "瓦力",
            "fields": ["author", "title"]
        }
    }
}
```

### query_string

所有字段匹配 "Python" 或 （匹配 "ElasticSearch" 和 "大法"）

```json
{
    "query": {
        "query_string": {
            "query": "(ElasticSearch AND 大法) OR Python"
        }
    }
}
```

### query_string.fields

author 或 title 字段匹配 "瓦力" 或 "ElasticSearch"

```json
{
    "query": {
        "query_string": {
            "query": "瓦力 OR ElasticSearch",
            "fields": ["author", "title"]
        }
    }
}
```

### term

author 匹配 "瓦力"

```json
{
    "query": {
        "term": {
            "author": "瓦力"
        }
    }
}
```

### range

1000 <= word_count <= 2000

```json
{
    "query": {
        "range": {
            "word_count": {
                "gte": 1000,
                "lte": 2000
            }
        }
    }
}
```

### range 日期

"2016-01-01" <= publish_date <= 现在

```json
{
    "query": {
        "range": {
            "publish_date": {
                "gte": "2016-01-01",
                "lte": "now"
            }
        }
    }
}
```
