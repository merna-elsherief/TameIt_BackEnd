package com.pro.tameit.groupTherapy.dto;

import com.pro.tameit.groupTherapy.models.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private Long id;
    private String text;
    private List<CommentDto> comments;
}
