# 📂 Cadoc 2060 API

API para ingestão, validação e envio de arquivos regulatórios Cadoc 2060, desenvolvida em Java 17 + Spring Boot.

Inspirada nas boas práticas e aprendizados com integração de sistemas e fluxos de arquivos regulatórios, permitindo criar um ambiente completo de teste e desenvolvimento usando Docker, MySQL e MinIO.

# 🔧 Tecnologias

Linguagem: Java 17

Framework: Spring Boot

Persistência: JPA + MySQL

Armazenamento de arquivos: MinIO (simulando AWS S3)

Containerização: Docker + Docker Compose

Logs: SLF4J

Documentação de API: Swagger

# 🏗 Estrutura do projeto

com.rgty.cadoc2060
├── controller      # Endpoints REST para upload, download e consulta de arquivos
├── service         # Lógica de negócio e orquestração do fluxo
├── repository      # Repositórios JPA para persistência
├── validator       # Validação de nomes e formatos de arquivos
├── processor       # Processamento adicional de arquivos (ex: split, status)
└── common          # Helpers, constantes e Singletons (ex: CadocStatusSingleton)

# 🚀 Funcionalidades

Upload e download de arquivos regulatórios

Validação do nome do arquivo conforme padrão esperado:

cadoc-2060_<EMPRESA>_YYYYMMDD_HHmmss.xml


Persistência de metadados de arquivos no banco (CadocFile)

Status de processamento de arquivos: received, uploadError, validationError, fileSplit

Logs padronizados para rastreabilidade

Documentação e testes de endpoints via Swagger

Ambiente dockerizado com MySQL e MinIO

# ⚡ Pré-requisitos

Docker

# 🐳 Rodando a aplicação completa

Abra um terminal dentro da raiz do projeto e suba todos os serviços (API + MySQL + MinIO) com um único comando:

docker-compose up -d

# 📄 Formato de arquivo para teste

Para que o upload seja aceito pela API, os arquivos Cadoc 2060 devem seguir o seguinte padrão de nomenclatura:

cadoc-2060_<NOME_EMPRESA>_<YYYYMMDD>_<HHMMSS>.xml

Exemplo válido:

cadoc-2060_FINTECH001_20251017_120000.xml  //FINTECH001 já está salva no banco para o exemplo

A API estará disponível em:

http://localhost:8080


Documentação Swagger (para testar endpoints):

http://localhost:8080/swagger-ui/index.html


Console MinIO:

http://localhost:9001


O script ./docker/mysql/init.sql inicializa os status obrigatórios (CADOC2060_RECEIVED, UPLOAD_ERROR, etc.) automaticamente.

📁 Estrutura de pastas do bucket (MinIO)
/inbox      → arquivos recebidos
/validated  → arquivos validados
/error      → arquivos com erro

📝 Boas práticas aplicadas

Separação de responsabilidades entre pacotes e classes

Injeção de dependência via construtor (@RequiredArgsConstructor)

Logs centralizados (LogGenerator)

Validação de nomes de arquivos e consistência de status

Dockerização para ambiente de teste consistente

Documentação de API via Swagger para testes e integração

🔗 Links úteis:

Repositório GitHub : https://github.com/Felipejf05/rgty-cadoc-2060.git