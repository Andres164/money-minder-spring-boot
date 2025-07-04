# Objetivo
Crear una aplicacion que te ayude a mejorar tus gastos.
Esto se logra teniendo un conocimiento completo de los gastsos que haces
nuestra aplicacion se encargara de llevar el segimiento de estos gastos


# Users
- id
- username
- email
- password


# Expenses
- id
- userId // Propietario del gasto
- categoryId
- amount
- currency
- description
- date

# Categories
- id
- name

# ExpenseTags
- expenseId
- tagId
- id

# Tags
- id
- name

# BudgetCategories
- budgetId
- categoryId
- id

# Budgets
- id
- userId // Propietario del presupuesto
- name
- budgetAmount
- currency
- amountUsed
- recurrence // El tiempo en dias que dura este presupuesto
- startDate
- state

# Notifications
- id
- usuerId
- content
- date


# Ejemplos

Dia actual
09 Junio

Presupuesto
100
entretenimiento
15 dias
01 Junio
80
activo

100
entretenimiento
15 dias
15 Mayo
80
historico


Categorias
- Despensas
- Entretenimiento
- Transporte
- Comida
- Deportes

Tags
-  Gasolina
- Cine
- Videojuegos
- Gym
- Ciclismo




Expense
- Compre un juego en steam
- $100
- Categoria: Entretenimiento
- Tag: Videojuegos

Budget #1
- Presupuesto mensual para entretenimiento
- Categorias Entretenimiento
- $100
- $0

Budget #2
- Presupuesto general mensual
- Categorias: Todas
- $1000
- $0


Expense
- Compre Stellar Blade
- $999
- Categoria: Entretenimiento
- Tag: Videojuegos