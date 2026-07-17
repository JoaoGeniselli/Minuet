# Minuet

Gerencie seu repertório musical com cifras, tonalidades e set-lists — direto no Android.

---

## Sobre

O Minuet é um aplicativo Android voltado para músicos que precisam organizar seu repertório de forma prática: cadastrar músicas com cifras, controlar tonalidades e montar set-lists para apresentações.

O app é projetado como **smartphone-first**, com suporte a layout adaptativo para tablets via `ListDetailPaneScaffold`, permitindo visualizar lista e detalhe lado a lado em telas maiores.

---

## Funcionalidades

### Repertório
- **Lista de músicas** — visualize todo o repertório com título, artista, tonalidade e andamento
- **Criação e edição de música** — cadastre título, artista, tags, tonalidade original, BPM, observações e a cifra em formato ChordPro
- **Visualização de cifra** — renderização inline dos acordes marcados no formato `[Am]Hoje eu [G]quero`, com transposição de tonalidade por semitons
- Ações destrutivas (excluir música) exibem confirmação modal antes de prosseguir

### Set-Lists
- **Lista de set-lists** — visualize todos os set-lists com título e tags
- **Criação e edição de set-list** — monte a ordem das músicas com suporte a drag-and-drop
- **Visualização de set-list** — percorra as músicas na sequência definida, com acesso rápido à cifra de cada uma
- Ações destrutivas (excluir set-list) exibem confirmação modal antes de prosseguir

---

## Modelo de Dados

### Song

| Campo | Tipo | Descrição |
|---|---|---|
| `id` | `String` (UUID) | Identificador único |
| `createdAt` | `Long` (timestamp) | Data de criação |
| `title` | `String` | Título da música |
| `artist` | `String` | Artista ou banda |
| `tags` | `List<String>` | Estilos, categorias ou etiquetas livres |
| `key` | `String?` | Tonalidade original (ex: `"C"`, `"G#"`) |
| `tonalAdjustment` | `Int` (0–11) | Semitons transpostos em relação à tonalidade original |
| `bpm` | `Int?` | Andamento em batidas por minuto |
| `notes` | `String?` | Observações livres |
| `content` | `String` | Conteúdo da cifra em formato ChordPro |

### SetList

| Campo | Tipo | Descrição |
|---|---|---|
| `id` | `String` (UUID) | Identificador único |
| `title` | `String` | Título do set-list |
| `tags` | `List<String>` | Categorias ou etiquetas |
| `createdAt` | `Long` (timestamp) | Data de criação |
| `orderedSongIds` | `List<String>` | IDs das músicas em ordem de apresentação |

---

## Tech Stack e Arquitetura

### Plataforma
- **Linguagem**: Kotlin
- **UI**: Jetpack Compose + Material3
- **Layout adaptativo**: `ListDetailPaneScaffold` para suporte a tablets
- **Banco de dados local**: Room
- **Injeção de dependência**: Hilt
- **Navegação**: Navigation Compose
- **Assíncrono**: Coroutines + Flow

### Arquitetura
Segue **Clean Architecture** com **MVVM** na camada de apresentação:

```
app/
└── src/main/java/com/dosei/music/repertoire/
    ├── data/    # Repositórios, DAOs, mappers
    ├── domain/  # Entidades, use cases, interfaces de repositório (puro Kotlin)
    ├── ui/      # Screens, ViewModels, Composables, navegação
    └── di/      # Módulos Hilt
```

A camada `domain` não possui dependências Android. A abstração dos repositórios prepara o app para integração futura com backend remoto (Firebase).

### Cifras
- Formato **ChordPro**: acordes marcados inline no corpo da letra (`[Am]Hoje eu [G]quero`)
- Renderização e transposição de tonalidade via **ktransposer**

---

## Roadmap

- [ ] Sincronização na nuvem via Firebase Firestore
- [ ] Autenticação de usuário (Firebase Auth)
- [ ] Compartilhamento de músicas e set-lists
- [ ] Modo de apresentação (tela cheia, auto-scroll)
- [ ] Suporte a múltiplos instrumentos / afinações alternativas
- [ ] Exportação de set-list em PDF
