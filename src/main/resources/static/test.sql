SELECT
    t.tourId,
    t.title,
    t.roadaddress,
    t.latitude,
    t.longitude,
    t.phoneno,
    t.tag,
    t.introduction,
    t.imagepath,
    CASE
        WHEN i.userId IS NOT NULL THEN 1
        ELSE 0
        END AS isInterested
FROM
    tour t
        LEFT JOIN
    interest i ON t.tourId = i.tourkey AND i.userId = (
        SELECT id
        FROM member
        WHERE email = 'singery00@naver.com'
        )
WHERE
        t.title LIKE '%숲%'