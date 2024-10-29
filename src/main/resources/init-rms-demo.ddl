CREATE TABLE IF NOT EXISTS rentalItem (
    id INT generated by default as identity PRIMARY KEY,
    serialNo VARCHAR(15) NOT NULL,
    itemName VARCHAR(45)
);

MERGE INTO rentalItem VALUES (1, 'A0001', '便利なパソコン');
MERGE INTO rentalItem VALUES (2, 'DSP001', '映りが良い液晶ディスプレイ');
MERGE INTO rentalItem VALUES (3, 'CX000A012', '握り抜群マウス');
MERGE INTO rentalItem VALUES (4, 'KJJ4763', 'REALFORCEのステキなキーボード');
MERGE INTO rentalItem VALUES (5, 'AO10DDDA', '激安ノートパソコン');
MERGE INTO rentalItem VALUES (6, 'SHOE0000001', '空飛べるスゴイ靴');
MERGE INTO rentalItem VALUES (7, 'RENTAL0001', 'レンタル品1号');
MERGE INTO rentalItem VALUES (8, 'ELBG00001', 'エルメスのお高いバック');
