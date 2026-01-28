# Consulta Tabela FIPE - Desafio Java Streams & API
Este projeto é uma aplicação de linha de comando (CLI) que interage com a API da Tabela FIPE para consultar o valor médio de veículos (carros, motos e caminhões).
O projeto foi desenvolvido como desafio do curso Java: trabalhando com lambdas, streams e Spring Framework da Alura/Oracle Next Education (ONE).

### 📋 Sobre o Projeto
O sistema permite realizar uma busca hierárquica por veículos:

- **Tipo**: Escolha entre Carros, Motos ou Caminhões.

- **Marca**: Listagem de todas as marcas da categoria escolhida.

- **Modelo**: Listagem dos modelos da marca selecionada, com funcionalidade de filtro por trecho do nome.

- **Preço Final**: O sistema identifica todos os anos disponíveis para aquele modelo e realiza consultas automáticas para exibir o valor de cada um.

### 🧰 Tecnologias
- Java 17 (ou superior).
- Gson (Biblioteca de processamento JSON).
- Parallelum FIPE API.

  

## 🛠️ O que foi Praticado e Implementado
#### 1. Java Streams & Lambdas
Utilizados para processar coleções de dados de forma eficiente e concisa:

- `.filter()`: Implementado para permitir que o usuário filtre uma lista extensa de modelos digitando apenas uma parte do nome (ex: "Palio").

- `.anyMatch()`: Utilizado na validação de códigos digitados pelo usuário, garantindo que a opção exista na lista retornada.

- `.map()`: Essencial no método `obterPrecos` para transformar uma lista de códigos de anos em uma lista de objetos Veiculo com preços reais.

- `Method Reference`: Utilizado em `veiculos.forEach(System.out::println)` para exibição limpa de dados.

#### 2. Consumo de API & HTTP Client
Integração com serviços externos para obtenção de dados em tempo real:

* **Java HttpClient**: Gerenciamento de requisições e respostas HTTP de forma síncrona.

* **Biblioteca Gson**: Utilizada para converter os JSONs recebidos em objetos Java.

* **Mapeamento de Tipos Complexos**: Uso de TypeToken para lidar com listas genéricas e objetos aninhados no processo de desserialização.
  

#### 3. Organização do Código e Modelagem
1. **Divisão de Responsabilidades**: Separação clara entre a lógica de serviço (FipeService) e a interação com o usuário (FipeView).

2. **Tratamento de Exceções**: Gerenciamento de erros de entrada de dados e falhas de comunicação com o servidor.

3. **Java Records**: Modelagem de DTOs (Data Transfer Objects) imutáveis para representar Marcas, Modelos e Veículos com sintaxe reduzida.



### 🏗️ Estrutura do Projeto
O projeto segue uma arquitetura organizada por pacotes:

* `main`: Ponto de entrada da aplicação.

* `model`: Contém os Records (Dados, Modelo, Veiculo) que definem a estrutura dos dados.

* `service`: Contém a lógica de consumo da API e processamento de dados.

* `view`: Gerencia a interface de texto, entradas do Scanner e exibição de resultados.
  

## 🚀 Fluxo da Aplicação
O projeto segue uma hierarquia de consulta lógica:

1. **Seleção de Tipo**: O usuário escolhe entre Carros, Motos ou Caminhões.

2. **Lista de Marcas**: O sistema busca e exibe as marcas disponíveis. O usuário seleciona uma pelo código.

3. **Lista de Modelos**: Exibe os modelos daquela marca.

4. **Filtro por Nome**: O usuário pode digitar um trecho do nome para filtrar a lista de modelos (implementado via Streams).

5. **Exibição de Valores**: Após selecionar o modelo, o sistema itera sobre todos os anos disponíveis e traz o preço médio de cada um, consolidando as informações em uma lista final.

## 🚀 Como Executar
1. Clone este repositório.

2. Certifique-se de estar utilizando o Java 17 ou superior.

3. Adicione a biblioteca `Gson` ao seu classpath (via Maven ou JAR).

4. Execute a classe `Main`.
