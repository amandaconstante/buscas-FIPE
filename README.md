# Consulta Tabela FIPE - Java CLI
Este projeto é uma aplicação de linha de comando (CLI) que interage com a API da Tabela FIPE para consultar o valor médio de veículos (carros, motos e caminhões).
O sistema foi desenvolvido com foco em boas práticas de programação orientada a objetos, consumo de APIs REST e manipulação de fluxos de dados com Java Streams.


### 📋 Sobre o Projeto
O sistema permite realizar uma busca hierárquica por veículos:

- **Tipo**: Escolha entre Carros, Motos ou Caminhões.

- **Marca**: Listagem de todas as marcas da categoria escolhida.

- **Modelo**: Listagem dos modelos da marca selecionada, com funcionalidade de filtro por trecho do nome.

- **Preço Final**: O sistema identifica todos os anos disponíveis para aquele modelo e realiza consultas automáticas para exibir o valor de cada um.

### 🧰 Tecnologias
- Java 21.
- **Jackson**: Biblioteca para processamento e desserialização de JSON.
- **HttpClient**: API nativa do Java para requisições HTTP.
- **Maven**: Gerenciador de dependências.
- Parallelum FIPE API.

---

## 🛠️ O que foi Praticado e Implementado

#### 1. Java Streams & Expressões Lambda
Utilizados para processar coleções de dados de forma eficiente e concisa:

- `.filter()`: Implementado para permitir que o usuário filtre uma lista extensa de modelos digitando apenas uma parte do nome (ex: "Palio").

- `.anyMatch()`: Utilizado na validação de códigos digitados pelo usuário, garantindo que a opção exista na lista retornada.

- `.map()`: Essencial no método `obterPrecos` para transformar uma lista de códigos de anos em uma lista de objetos Veiculo com preços reais.
  
-  `.sorted()`: Organização das listas exibidas no console por ordem de código ou nome.

- `Method Reference`: Utilizado em `veiculos.forEach(System.out::println)` para exibição limpa de dados.

#### 2. Arquitetura e Padrões de Projeto
O projeto foi estruturado utilizando princípios de design de software:
- **Injeção de Dependência**: As classes de serviço recebem suas dependências via construtor, facilitando o desacoplamento.
- **Abstração com Interfaces**: Uso de interfaces para definir contratos de conversão de dados, permitindo que a implementação do motor de JSON seja trocada sem afetar a lógica de negócio.
- **Generics**: Implementação de métodos genéricos (`<T>`) para conversão de dados, permitindo que um único método manipule diferentes tipos de objetos e listas.

#### 3. Integração e Desserialização de Dados
- **ObjectMapper (Jackson)**: Uso para converter JSON em objetos Java e coleções complexas através de `CollectionType`.
- **Java Records**: Uso de DTOs imutáveis para representação concisa dos dados da API (`Dados`, `Modelos`, `Veiculo`).
- **Anotações Jackson**: Uso de `@JsonProperty` e `@JsonIgnoreProperties` para mapear campos da API para o modelo Java de forma eficiente.

---

### 🏗️ Estrutura do Projeto
A organização segue uma separação clara de responsabilidades:

* **`main`**: Ponto de entrada que orquestra a injeção de dependências.
* **`model`**: Definições de dados (Records).
* **`service`**: 
    - `ConsumoApi`: Responsável estritamente pela comunicação HTTP.
    - `ConverteDados`: Implementação da interface de conversão de JSON.
    - `FipeService`: Lógica de negócio e regras da Tabela Fipe.
* **`view`**: Interface de usuário via console e captura de entradas.

---
  

## 🚀 Fluxo da Aplicação
O projeto segue uma hierarquia de consulta lógica:

1. **Seleção de Tipo**: O usuário escolhe entre Carros, Motos ou Caminhões.

2. **Lista de Marcas**: O sistema busca e exibe as marcas disponíveis. O usuário seleciona uma pelo código.

3. **Lista de Modelos**: Exibe os modelos daquela marca.

4. **Filtro por Nome**: O usuário pode digitar um trecho do nome para filtrar a lista de modelos (implementado via Streams).

5. **Exibição de Valores**: Após selecionar o modelo, o sistema itera sobre todos os anos disponíveis e traz o preço médio de cada um, consolidando as informações em uma lista final.

---

## 🚀 Como Executar
1. Clone este repositório.
2. Certifique-se de estar utilizando o Java 17 ou superior.
3. Importe o projeto como um projeto Maven para que as dependências do Jackson sejam baixadas automaticamente.
4. Para o funcionamento do projeto, adicione as dependências do Jackson ao seu `pom.xml`:
```xml
<dependencies>
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
        <version>2.20.1</version>
        <scope>compile</scope>
    </dependency>
</dependencies>
```
5. Execute a classe `Main`.

*Projeto desenvolvido como desafio prático da formação Java Alura / Oracle Next Education (ONE), do curso Java: trabalhando com lambdas, streams e Spring Framework da Alura/Oracle Next Education (ONE).*
