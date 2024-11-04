# Sistema Bancário Distribuído com Java RMI

Este projeto implementa um sistema bancário distribuído usando o modelo cliente/servidor com Java RMI (Remote Method Invocation). O servidor (`BankServer`) oferece serviços bancários que podem ser acessados remotamente por clientes, incluindo operações de depósito, saque e consulta de saldo.

## Estrutura do Projeto

O projeto é composto pelas seguintes classes principais:

- `BankServer.java`: Servidor principal que registra o serviço bancário no RMI Registry.
- `BankServiceImpl.java`: Implementação dos métodos do serviço bancário.
- `BankService.java`: Interface do serviço bancário.
- `ATMClientCashier.java`: Cliente que realiza operações bancárias no caixa eletrônico.
- `ClientAgency.java`: Cliente que representa uma agência, também realiza operações bancárias.
- `RegistryCheck.java`: Classe que verifica os serviços registrados no RMI Registry.
- `ConcurrencyTest.java`: Teste para simular operações concorrentes de múltiplos clientes.

## Pré-requisitos

- **Java Development Kit (JDK)**: Certifique-se de que o JDK (versão 11 ou superior) está instalado e configurado no sistema.
- **Configurando o CLASSPATH**: Verifique se o diretório dos arquivos `.java` está no classpath ou ajuste o classpath conforme necessário.

## Compilação

1. Abra um terminal e navegue até o diretório onde os arquivos `.java` estão localizados.

2. Compile todos os arquivos utilizando o comando:
   ```bash
   javac *.java
   
## Execução do Programa

1. Iniciar o Registro RMI
O RMI Registry é responsável por gerenciar o registro e descoberta dos serviços remotos. Em um terminal separado, inicie o rmiregistry:
   ```bash
   rmiregistry
Deixe esta janela aberta, pois o rmiregistry precisa estar ativo durante a execução do servidor e dos clientes.

2. Iniciar o Servidor
Em outro terminal, execute o servidor:
   ```bash
   java BankServer

O servidor agora estará pronto para aceitar conexões de clientes e exibirá uma mensagem indicando que o BankService foi registrado no RMI Registry.

3. Executar cliente
Em uma nova janela de terminal, execute um dos clientes para realizar operações no sistema bancário.

Para executar o `ATMClientCashier`, use:

   ```bash
   java ATMClientCashier
   ```
Para executar o `ClientAgency`, use:
   ```bash
java ClientAgency
   ```

4. Teste de Concorrência
Para testar o comportamento do sistema sob operações simultâneas, execute a classe `ConcurrencyTest`, que cria múltiplas threads para realizar depósitos e saques em uma mesma conta.
```bash
java ConcurrencyTest
   ```
O saldo final será exibido após o término do teste, confirmando a integridade das operações concorrentes.

## Observações
- Certifique-se de que o `rmiregistry` esteja rodando antes de iniciar o servidor.
- Para executar em múltiplas máquinas, ajuste o código para aceitar IPs específicos, se necessário, e configure as portas do RMI.

## Mensagens de Log e Controle de Idempotência
Durante a execução, o sistema exibe mensagens de log para monitoramento das operações realizadas e controle de idempotência, evitando a duplicação de transações. Isso é especialmente importante para verificar a consistência dos dados em operações concorrentes.
