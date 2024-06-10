CREATE TABLE trade (
                        tradeNo INT AUTO_INCREMENT PRIMARY KEY,
                        sellerID VARCHAR(255) NOT NULL,
                        title VARCHAR(255) NOT NULL,
                        description TEXT NOT NULL,
                        price INT NOT NULL,
                        categoryID VARCHAR(255) NOT NULL,
                        selling VARCHAR(255) NOT NULL,
                        status VARCHAR(255) NOT NULL,
                        conditions VARCHAR(255) NOT NULL,
                        tradeMethod VARCHAR(255) NOT NULL,
                        locations VARCHAR(255) NOT NULL,
                        readCount INT DEFAULT 0,
                        likes INT DEFAULT 0
);

CREATE TABLE trade_images (
                              id INT AUTO_INCREMENT PRIMARY KEY,
                              tradeNo INT NOT NULL,
                              imagePath VARCHAR(255) NOT NULL,
                              FOREIGN KEY (tradeNo) REFERENCES trade(tradeNo) ON DELETE CASCADE
);

CREATE TABLE report (
                        reportId INT AUTO_INCREMENT PRIMARY KEY,
                        tradeNo INT NOT NULL,
                        reason VARCHAR(255) NOT NULL,
                        details TEXT,
                        FOREIGN KEY (tradeNo) REFERENCES trade(tradeNo) ON DELETE CASCADE
);
