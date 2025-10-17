-- Para popular a empresa padrão do exemplo
INSERT INTO cadoc_company (company_name)
SELECT 'FINTECH001'
WHERE NOT EXISTS (SELECT 1 FROM cadoc_company WHERE company_name = 'FINTECH001');

-- Para popular os status do CADOC2060
INSERT INTO cadoc_status (nemotecnico)
SELECT 'CADOC2060_RECEIVED'
WHERE NOT EXISTS (SELECT 1 FROM cadoc_status WHERE nemotecnico = 'CADOC2060_RECEIVED');

INSERT INTO cadoc_status (nemotecnico)
SELECT 'CADOC2060_UPLOAD_ERROR'
WHERE NOT EXISTS (SELECT 1 FROM cadoc_status WHERE nemotecnico = 'CADOC2060_UPLOAD_ERROR');

INSERT INTO cadoc_status (nemotecnico)
SELECT 'CADOC2060_VALIDATION_ERROR'
WHERE NOT EXISTS (SELECT 1 FROM cadoc_status WHERE nemotecnico = 'CADOC2060_VALIDATION_ERROR');

INSERT INTO cadoc_status (nemotecnico)
SELECT 'CADOC2060_FILE_SPLIT'
WHERE NOT EXISTS (SELECT 1 FROM cadoc_status WHERE nemotecnico = 'CADOC2060_FILE_SPLIT');