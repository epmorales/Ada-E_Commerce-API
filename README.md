# 🛒 Ada E-Commerce API

### Projeto desenvolvido para o programa **Ada Tech**, com o objetivo de implementar um sistema completo de **E-Commerce**, abrangendo o fluxo de **clientes, produtos, pedidos, pagamentos, entregas, cupons de desconto e notificações**.

---

## 🚀 Visão Geral

A **Ada Tech** pretendeu realizar a venda de produtos através de um E-Commerce e, para isso, contratou nossa equipe para desenvolver toda a estrutura necessária.

O sistema foi construído com foco em **boas práticas de Programação Orientada a Objetos (POO)**, **organização modular** e **regras de negócio bem definidas**.

---

## 🧩 Funcionalidades Principais

### 👤 Clientes
- Cadastrar novos clientes (com documento de identificação obrigatório)
- Listar clientes cadastrados
- Atualizar dados dos clientes
- Exclusão **não permitida** (mantidos como histórico)

### 📦 Produtos
- Cadastrar novos produtos
- Listar produtos cadastrados
- Atualizar dados dos produtos
- Exclusão **não permitida** (mantidos como histórico)

### 🧾 Pedidos
- Criar pedidos para clientes
- Adicionar itens (produtos) ao pedido, informando **quantidade e preço**
- Remover itens do pedido
- Alterar quantidade dos itens
- Finalizar pedido (com validações de valor e itens)
- Realizar pagamento do pedido
- Realizar entrega do pedido

### 🎟️ Cupons de Desconto
- Criar cupons de desconto
- Aplicar cupons de desconto aos pedidos
- Listar cupons de desconto disponíveis
- Atualizar cupons
- Expirar cupons automaticamente

### 💰 Regras de Desconto
- Regras simples: aplicação direta de desconto fixo ou percentual
- Regras compostas: combinações progressivas ou condicionais

### 📧 Notificações
- Envio de notificação por **e-mail** nas etapas de:
  - Finalização do pedido
  - Pagamento realizado
  - Entrega do pedido

---

## ⚖️ Regras de Negócio

1. Todo cliente deve possuir **documento de identificação**.  
2. O pedido deve registrar **data de criação** e iniciar com status **ABERTO**.  
3. Pedidos **ABERTOS** podem:
   - Receber itens
   - Alterar quantidades
   - Remover itens  
4. O **valor de venda** do produto pode ser diferente do valor original.  
5. Para **finalizar um pedido**, ele deve:
   - Conter ao menos **um item**
   - Ter valor total **maior que zero**
   - Ter o status de pagamento alterado para **"Aguardando pagamento"**
   - Notificar o cliente via e-mail  
6. O **pagamento** só pode ocorrer em pedidos com status **"Aguardando pagamento"**.  
   - Após o pagamento, o status é alterado para **"Pago"**
   - O cliente é notificado  
7. Após o pagamento, o pedido pode ser **entregue**, com o status alterado para **"Finalizado"**.  
   - O cliente é novamente notificado  
8. **Cupons de desconto** devem ser validados antes da aplicação:
   - Verificação de validade (data de expiração)
   - Verificação de uso (se já foi utilizado)
   - Aplicação correta no valor total do pedido  

---

## 🧠 Tecnologias Utilizadas

- **Java**  
- **Paradigma de Programação Orientada a Objetos (POO)**
- **Padrões de Repositório e Entidades**
- **Streams API (Java 8+)**
- **Notificações simuladas via e-mail**
- **Manipulação de datas e status de pedidos**

---

## 💬 Fluxo Simplificado do Sistema

1. Cliente é cadastrado com CPF obrigatório.  
2. Produtos são registrados no catálogo.  
3. Cliente realiza pedido.  
4. Adiciona produtos com quantidades e preços.  
5. Pedido é finalizado e o status muda para **“Aguardando pagamento”**.  
6. Cliente realiza o pagamento (status: **“Pago”**).  
7. Pedido é entregue (status: **“Finalizado”**).  
8. Cliente recebe notificações automáticas por e-mail em todas as etapas.

---

## 📜 Licença

Este projeto foi desenvolvido para fins **educacionais** no contexto do curso de **Programação Orientada a Objetos — Ada Tech**, sem fins comerciais.

---
⭐ **Se este projeto te ajudou, deixe uma estrela no repositório!**




