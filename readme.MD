# SecureUserAuth

Sistema de autenticação simples utilizando React/TypeScript no frontend e Java/Spring Boot no backend, com tokens JWT(access/refresh), tratamento de erros, cargos, criação de usuários e postagens.

Como usar (Vscode):
- Inicie o Spring Boot pela extensão e o React pelo terminal:
    - cd secureuserauth-front | npm install | npm run dev
- Acesse o endereço localhost:5173 no navegador
- Registre-se ou entre pelo usuário admin criado automaticamente:
    - email: admin@email.com | senha: admin
- Na Home, é possível criar uma postagem e visualizar todas as postagens. No perfil, somente as suas são mostradas
- É possível apagar suas postagens e o usuário admin pode apagar qualquer postagem

Problemas conhecidos: 
- Ao expirar o token de acesso, é necessário recarregar a páginas duas vezes para surtir as mudanças
- Mensagens de erro no login e registro não são mostradas corretamente

Possíveis implementações: 
- Elevar cargos de usuários
- Redefinir senha
- Salvar access token no banco
- Blacklist de access token
- Tornar um usuário moderador