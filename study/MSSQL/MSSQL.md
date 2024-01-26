# SQL语句案例

## 案例1 使用,拼接的字段查询

案例描述：

> 一个字段存了多个ID并且使用`,`分隔，如何查询

解决方法：

```sql
Id in (SELECT Id FROM Bggl_Nxjl CROSS APPLY STRING_SPLIT(Zzbmid, ',') AS bm WHERE bm.value = 102)
```

## 案例2 拼接字段

案例描述:

> 需要查询一条记录，并要显示他关联的几条子记录数据

解决办法：

```
SELECT t1.*,t3.Fwryxms,t3.Fwryids,t3.Fwrybmmcs,t3.Fwrybmids from Fw_Pxdjxx t1 LEFT JOIN(SELECT Pxdjid ,
STUFF((SELECT ','+Fwryxm FROM Fw_Fwdjxx WHERE Fw_Fwdjxx.Pxdjid = t1.Pxdjid FOR XML PATH('')), 1, 1, '') AS Fwryxms,
STUFF((SELECT ','+CAST(Fwryid AS VARCHAR(10)) FROM Fw_Fwdjxx WHERE Fw_Fwdjxx.Pxdjid = t1.Pxdjid FOR XML PATH('')), 1, 1, '') AS Fwryids,
STUFF((SELECT ','+Fwrybmmc FROM Fw_Fwdjxx WHERE Fw_Fwdjxx.Pxdjid = t1.Pxdjid FOR XML PATH('')), 1, 1, '') AS Fwrybmmcs, 
STUFF((SELECT ','+CAST(Fwrybmid AS VARCHAR(10)) FROM Fw_Fwdjxx WHERE Fw_Fwdjxx.Pxdjid = t1.Pxdjid FOR XML PATH('')), 1, 1, '') AS Fwrybmids
FROM Fw_Fwdjxx t1 WHERE 1 = 1  GROUP BY Pxdjid ) t3 ON t1.Id = t3.Pxdjid where 1=1 
```

## 案例3 查询Id是否在 某张表的 某个字段里，并且是用逗号分隔的

案例描述：

> 查询Id是否在 某张表的 某个字段里，并且是用逗号分隔的

解决办法:

```
SELECT Id,Jgjc FROM Xtwh_Yh_Zzjg WHERE id IN (SELECT  CAST(value AS INT) FROM STRING_SPLIT((SELECT TOP 1 Fwbmid FROM Fw_Sz_Qtpzxx), ',')
```

