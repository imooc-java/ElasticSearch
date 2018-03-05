# 5-2 filter

## Filter context

在查询过程中，只判断该文档是否**满足**条件，只有 Yes 或者 No。

http://127.0.0.1:9200/book/_search

* 请求

```json
{
    "query": {
        "bool": {
            "filter": {
                "term": {
                    "word_count": 1000
                }
            }
        }
    }
}
```

* 返回

```json
{
    "took": 15,
    "timed_out": false,
    "_shards": {
        "total": 5,
        "successful": 5,
        "skipped": 0,
        "failed": 0
    },
    "hits": {
        "total": 5,
        "max_score": 0,
        "hits": [
            {
                "_index": "book",
                "_type": "novel",
                "_id": "8",
                "_score": 0,
                "_source": {
                    "author": "张三",
                    "title": "移魂大法",
                    "word_count": 1000,
                    "publish_date": "2012-10-01"
                }
            },
            {
                "_index": "book",
                "_type": "novel",
                "_id": "9",
                "_score": 0,
                "_source": {
                    "author": "张三丰",
                    "title": "太极拳",
                    "word_count": 1000,
                    "publish_date": "2002-10-01"
                }
            },
            {
                "_index": "book",
                "_type": "novel",
                "_id": "4",
                "_score": 0,
                "_source": {
                    "author": "牛魔王",
                    "title": "芭蕉扇",
                    "word_count": 1000,
                    "publish_date": "2000-10-01"
                }
            },
            {
                "_index": "book",
                "_type": "novel",
                "_id": "6",
                "_score": 0,
                "_source": {
                    "author": "李四",
                    "title": "ElasticSearch大法好",
                    "word_count": 1000,
                    "publish_date": "2002-10-01"
                }
            },
            {
                "_index": "book",
                "_type": "novel",
                "_id": "11",
                "_score": 0,
                "_source": {
                    "author": "孙悟空",
                    "title": "七十二变",
                    "word_count": 1000,
                    "publish_date": "2000-10-01"
                }
            }
        ]
    }
}
```