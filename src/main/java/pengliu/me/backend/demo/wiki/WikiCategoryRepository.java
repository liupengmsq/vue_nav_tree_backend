package pengliu.me.backend.demo.wiki;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface WikiCategoryRepository extends JpaRepository<WikiCategory, Long> {

}
