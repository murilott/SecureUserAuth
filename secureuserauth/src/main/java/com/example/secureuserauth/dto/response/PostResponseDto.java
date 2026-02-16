
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostResponseDto {
    private Long id;

    private String authorName;
    private String authorId;

    private String title;

    private String content;
    
    private LocalDateTime createdAt;
}