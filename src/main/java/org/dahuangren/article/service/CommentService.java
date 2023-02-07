package org.dahuangren.article.service;

import org.dahuangren.article.dao.CommentRepository;
import org.dahuangren.article.po.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Queue;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public void saveComment(Comment comment) {
        // 如果不指定主键，MongoDB会自动生成主键
        commentRepository.save(comment);
    }

    public void updateComment(Comment comment) {
        commentRepository.save(comment);
    }

    public void deleteCommentById(String id) {
        commentRepository.deleteById(id);
    }

    public List<Comment> findCommentList() {
        return commentRepository.findAll();
    }

    public Comment findCommentById(String id) {
        return commentRepository.findById(id).get();
    }

    public Page<Comment> findCommentListByParentid(String parentid, int page, int size) {
        return commentRepository.findByParentid(parentid, PageRequest.of(page-1, size));
    }

    public void updateCommentLikenum(String id) {
        // 查询条件
        Query query = Query.query(Criteria.where("_id").is(id));
        // 更新条件
        Update update = new Update();
        update.inc("likenum");
        mongoTemplate.updateFirst(query, update, Comment.class);
    }
}
