-- member_roles 테이블 삭제 (CASCADE 옵션 추가)
DROP TABLE IF EXISTS member_roles CASCADE;

-- member_roles 테이블 생성
CREATE TABLE member_roles (
                              member_id INT,
                              roles VARCHAR(32),
                              FOREIGN KEY (member_id) REFERENCES member (id) ON DELETE CASCADE
);