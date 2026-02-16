@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreatePostRequestDto {
    @NotNull(message = "AuthorId must not be blank")
    private Long authorId;

    @NotBlank(message = "Title must not be blank")
    private String title;

    @NotBlank(message = "Content must not be blank")
    private String content;
}