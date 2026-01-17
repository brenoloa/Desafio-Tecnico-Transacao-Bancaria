# api-transacao

## Java/Gradle toolchain (Java 21 no projeto)

Este projeto está configurado para **compilar e testar com Java 21** via Gradle Toolchain, armazenando a JDK baixada em `./.jdks`.

> Importante: o **Gradle Wrapper (9.x)** precisa rodar com **Java 17+** para iniciar. Depois que o Gradle inicia, ele resolve/baixa a **JDK 21** para compilar.

### Requisitos
- Ter **Java 17+** disponível no seu `JAVA_HOME`/`PATH` (somente para executar o Gradle).

### Onde fica o toolchain
- Diretório local no projeto: `./.jdks` (ignorado no Git)
- Configuração: `gradle.properties` + bloco `java { toolchain { ... } }` em `build.gradle`

## Banco H2

A aplicação usa **H2 em memória** com console habilitado.

- Console: `http://localhost:8080/h2-console`
- JDBC URL (padrão): `jdbc:h2:mem:transacaodb`
- Usuário: `sa`
- Senha: _(vazia)_

As configs estão em `src/main/resources/application.properties`.

