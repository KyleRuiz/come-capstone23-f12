package io.f12.notionlinkedblog.domain.post;

import static javax.persistence.FetchType.*;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.util.StringUtils;

import io.f12.notionlinkedblog.domain.PostTimeEntity;
import io.f12.notionlinkedblog.domain.user.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Getter
@Table(name = "posts")
@SequenceGenerator(
	name = "post_seq_generator",
	sequenceName = "post_seq",
	allocationSize = 1
)
public class Post extends PostTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_seq_generator")
	private Long id;
	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "user_id")
	@NotNull
	private User user;
	@NotBlank
	private String title;
	@NotBlank
	private String content;
	private String thumbnail;
	private Long viewCount;

	@Builder
	public Post(User user, String title, String content, String thumbnail, Long viewCount) {
		this.user = user;
		this.title = title;
		this.content = content;
		this.thumbnail = thumbnail;
		this.viewCount = viewCount;
	}

	public void editPost(String title, String content, String thumbnail) { // 비어있는 데이터는 예외처리
		if (StringUtils.hasText(title)) {
			this.title = title;
		}
		if (StringUtils.hasText(content)) {
			this.content = content;
		}
		if (StringUtils.hasText(thumbnail)) {
			this.thumbnail = thumbnail;
		}
	}

	public boolean isSameUser(Long userId) {
		return Objects.equals(user.getId(), userId);
	}
}