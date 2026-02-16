
@Slf4j
@NoArgsConstructor
@Setter(AccessLevel.PRIVATE)
@Getter
@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @NotBlank(message = "Title must not be blank")
    private String title;

    @NotBlank(message = "Content must not be blank")
    private String content;

    private LocalDateTime createdAt;

    public static Post newPost(User author, String title, String content) {
        Post post = new Post();
        post.setAuthor(author);
        post.setTitle(title);
        post.setContent(content);
        post.setCreatedAt(LocalDateTime.now());

        return post;
    }
}
