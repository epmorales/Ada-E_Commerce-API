📦 Ada Commerce – E-Commerce

Este projeto foi desenvolvido como exercício prático para aplicar os princípios de Programação Orientada a Objetos (POO) e boas práticas de design de software em Java.

O sistema simula um E-Commerce simplificado, onde é possível gerenciar clientes, produtos e pedidos, respeitando regras de negócio reais (cadastro, atualização, finalização de pedidos e notificações).

🎯 Objetivos Técnicos

No projeto foram aplicados os seguintes conceitos:

✅ Abstração

Cada entidade (Cliente, Produto, Pedido, ItemPedido) representa apenas os atributos e comportamentos essenciais.

O sistema esconde detalhes internos e expõe apenas métodos necessários ao uso externo.

✅ Encapsulamento

Os atributos das classes são privados (private).

O acesso é feito por métodos get e set, garantindo segurança e controle.

✅ Interfaces

Criadas para padronizar contratos de comportamento:

Identificavel: garante que todas as entidades tenham um UUID único.

Notificacao: define a forma de envio de mensagens (e-mail, SMS, etc).

Repositorio<T>: define operações genéricas de persistência em memória.

✅ Generics

O repositório (Repositorio<T>) foi implementado de forma genérica, permitindo o armazenamento de qualquer tipo de entidade (Cliente, Produto, Pedido) sem duplicação de código.

✅ Polimorfismo

A interface Notificacao permite múltiplas implementações.

Atualmente o projeto possui a classe EmailNotificacao, mas futuramente pode incluir SMSNotificacao, PushNotificacao etc., sem alterar o código do núcleo.

✅ SOLID

S (Single Responsibility): cada classe tem uma única responsabilidade (ex.: Pedido lida com regras de pedido, Repositorio com persistência).

O (Open/Closed): o sistema está aberto para extensão (novos tipos de notificações, novas formas de persistência), mas fechado para modificação.

L (Liskov Substitution): classes que implementam Repositorio<T> ou Notificacao podem ser substituídas sem impacto.

I (Interface Segregation): cada interface foi criada pequena e específica, sem métodos desnecessários.

D (Dependency Inversion): o código depende de abstrações (Notificacao, Repositorio) e não de implementações concretas.

🖥️ Funcionamento

Clientes: cadastrar, listar e atualizar.

Produtos: cadastrar, listar e atualizar.

Pedidos: criar, adicionar/remover itens, alterar quantidade, finalizar, pagar e entregar.

Menu: implementado em formato de “caixa” com cores no console (verde para títulos, branco para itens).

🎨 Diferenciais

Menu interativo organizado em blocos (Clientes, Produtos, Pedidos).

Estilização colorida no console (ANSI escape codes).

Estrutura extensível, facilitando futuras melhorias como persistência em banco de dados ou novos canais de notificação.

🚀 Como Executar

Clone o repositório na sua máquina.

Importe o projeto em sua IDE favorita.

Rode a classe Main.java.

Utilize o menu para navegar pelas opções disponíveis.
