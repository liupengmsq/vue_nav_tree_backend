package pengliu.me.backend.demo.wiki;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pengliu.me.backend.demo.ResponseDocument;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class WikiController {
    @Autowired
    private WikiService wikiService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/wiki/category")
    public ResponseDocument<List<WikiCategory>> getAllWikiCategories() {
        return ResponseDocument.successResponse(wikiService.getAllWikiCategories());
    }

    @GetMapping("/wiki")
    public ResponseDocument<List<WikiDTO>> getAllWikiPages() {
        List<Wiki> wikiList = wikiService.getAllWikiPages();
        return ResponseDocument.successResponse(wikiList.stream().map(this::convertToDto).collect(Collectors.toList()));
    }

    @GetMapping("/wiki/{id}")
    public ResponseDocument<WikiDTO> getWikiPageById(@PathVariable Long id) {
        return ResponseDocument.successResponse(convertToDto(wikiService.getWikiById(id)));
    }

    @PostMapping("/wiki")
    public void createWikiPage() {
        WikiCategory wikiCategory = wikiService.getWikiCategoryById(1L);
        Wiki wiki = new Wiki();
        wiki.setTitle("123123");
        wiki.setMarkdownContent("hhahahahahahah");
        wiki.setWikiCategory(wikiCategory);
        wikiService.createUpdateWikiPage(wiki);
    }

    @PutMapping("/wiki")
    public void updateWikiPage() {
        Wiki wiki = wikiService.getWikiById(3L);
        wiki.setMarkdownContent("更新markdown内容111");
        wikiService.createUpdateWikiPage(wiki);
    }

    private WikiDTO convertToDto(Wiki wiki) {
        modelMapper.typeMap(Wiki.class, WikiDTO.class)
            .addMappings(mapper -> {
                mapper.map(src -> src.getWikiCategory().getId(), WikiDTO::setCategoryId);
                mapper.map(src -> src.getWikiCategory().getCategoryName(), WikiDTO::setCategoryName);
            });
        return modelMapper.map(wiki, WikiDTO.class);
    }
}
