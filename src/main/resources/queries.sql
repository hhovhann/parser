-- (1) Write MySQL query to find IPs that mode more than a certain number of requests for a given time period.

-- The output will have 192.168.11.231 and not only :). In reality returns 192.168.106.134, 192.168.11.231
-- Has 200 or more requests between 2017-01-01 15:00:00 and 2017-01-01 15:59:59

SELECT log.ip_address, COUNT(log.ip_address) AS REQUEST_COUNT
FROM access_log log
WHERE log.start_date > '2017-01-01 15:00:00.000'
  AND log.start_date < '2017-01-01 15:59:59.000'
GROUP BY log.ip_address
HAVING COUNT(log.ip_address) > 200;

-- The output will have 192.168.102.136 and not only :).
-- In reality returns 192.168.102.136, 192.168.162.248, 192.168.199.209,  192.168.38.77, 192.168.62.176, 192.168.203.111, 192.168.185.164, 192.168.52.153, 192.168.129.191, 192.168.206.141, 192.168.51.205, 192.168.143.177, 192.168.31.26, 192.168.219.10, 192.168.33.16
-- Has 200 or more requests between 2017-01-01 15:00:00 and 2017-01-01 15:59:59
SELECT log.ip_address, COUNT(log.ip_address) AS REQUEST_COUNT
FROM access_log log
WHERE log.start_date > '2017-01-01 00:00:00.000'
  AND log.start_date < '2017-01-01 23:59:59.000'
GROUP BY log.ip_address
HAVING COUNT(log.ip_address) > 500;

--     Ex: Write SQL to find IPs that made more than 100 requests starting from 2017-01-01.13:00:00 to 2017-01-01.14:00:00.
SELECT log.ip_address, COUNT(log.ip_address) AS REQUEST_COUNT
FROM access_log log
WHERE log.start_date > '2017-01-01 13:00:00.000'
  AND log.start_date < '2017-01-01 14:00:00.000'
GROUP BY log.ip_address
HAVING COUNT(log.ip_address) > 100;

-- (2) Write MySQL query to find requests made by a given IP.
SELECT REQUEST
FROM ACCESS_LOG
WHERE IP_ADDRESS = '192.168.102.136';