package com.cy.store.service.impl;

import com.cy.store.entity.Address;
import com.cy.store.entity.Cart;
import com.cy.store.entity.Product;
import com.cy.store.mapper.AddressMapper;
import com.cy.store.mapper.CartMapper;
import com.cy.store.mapper.ProductMapper;
import com.cy.store.service.IAddressService;
import com.cy.store.service.ICartService;
import com.cy.store.service.ex.*;
import com.cy.store.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class CartServiceImpl implements ICartService {

    @Autowired
    private  CartMapper cartMapper;
    @Autowired
    private ProductServiceImpl productService;
    @Override
    public void addToCart(Integer uid, Integer pid, Integer amount, String username) {
        Cart result=cartMapper.findByUidAndPid(uid, pid);
        Date now = new Date();
        if(result==null) {//表示从来被添加到购物车
            Cart cart = new Cart();//空的
            //补全数据
            cart.setUid(uid);
            cart.setPid(pid);
            cart.setNum(amount);
//            cart.setCreatedUser(username);
//            cart.setModifiedUser(username);
            //补全价格
            Product product = productService.findById(pid);
            cart.setPrice(product.getPrice());
            //补全四个日志
            cart.setCreatedUser(username);
            cart.setCreatedTime(now);
            cart.setModifiedUser(username);
            cart.setModifiedTime(now);
            //执行输入的插入操作
//            cartMapper.insert(cart);
            //执行输入的插入操作
            Integer rows = cartMapper.insert(cart);
            if (rows != 1) {
                throw new InsertException("插入数据时产生未知的异常");
            }
        }else {//已存在，则更新num值
                Integer cid = result.getCid();
                Integer num = result.getNum() + amount;
                Integer rows = cartMapper.updateNumByCid(cid, num, username, now);
                if (rows != 1) {
                    throw new UpdateException("更新数据时产生未知的异常");
                }
            }


        }

    @Override
    public List<CartVO> getVOByUid(Integer uid) {
        return cartMapper.findVOByUid(uid);
    }

    @Override
    public Integer addNum(Integer cid, Integer uid, String username) {
        // 调用findByCid(cid)根据参数cid查询购物车数据
        Cart result = cartMapper.findByCid(cid);
        // 判断查询结果是否为null
        if (result == null) {
            // 是：抛出CartNotFoundException
            throw new CartNotFoundException("尝试访问的购物车数据不存在");
        }

        // 判断查询结果中的uid与参数uid是否不一致
        if (!result.getUid().equals(uid)) {
            // 是：抛出AccessDeniedException
            throw new AccessDeniedException("非法访问");
        }

        // 可选：检查商品的数量是否大于多少(适用于增加数量)或小于多少(适用于减少数量)
        // 根据查询结果中的原数量增加1得到新的数量num
        Integer num = result.getNum() + 1;

        // 创建当前时间对象，作为modifiedTime
        Date now = new Date();
        // 调用updateNumByCid(cid, num, modifiedUser, modifiedTime)执行修改数量
        Integer rows = cartMapper.updateNumByCid(cid, num, username, now);
        if (rows != 1) {
            throw new InsertException("修改商品数量时出现未知错误，请联系系统管理员");
        }

        // 返回新的数量
        return num;
    }
    @Override
    public Integer reduceNum(Integer cid, Integer uid, String username) {
        // 调用findByCid(cid)根据参数cid查询购物车数据
        Cart result = cartMapper.findByCid(cid);
        // 判断查询结果是否为null
        if (result == null) {
            // 是：抛出CartNotFoundException
            throw new CartNotFoundException("尝试访问的购物车数据不存在");
        }

        // 判断查询结果中的uid与参数uid是否不一致
        if (!result.getUid().equals(uid)) {
            // 是：抛出AccessDeniedException
            throw new AccessDeniedException("非法访问");
        }

        // 可选：检查商品的数量是否大于多少(适用于增加数量)或小于多少(适用于减少数量)
        // 根据查询结果中的原数量增加1得到新的数量num
        Integer num = result.getNum() - 1;

        // 创建当前时间对象，作为modifiedTime
        Date now = new Date();
        // 调用updateNumByCid(cid, num, modifiedUser, modifiedTime)执行修改数量
        Integer rows = cartMapper.updateNumByCid(cid, num, username, now);
        if (rows != 1) {
            throw new InsertException("修改商品数量时出现未知错误，请联系系统管理员");
        }

        // 返回新的数量
        return num;
    }

    @Override
    public List<CartVO> getVOByCids(Integer uid, Integer[] cids) {
        List<CartVO> list = cartMapper.findVOByCids(cids);
        /**
         for (CartVO cart : list) {
         if (!cart.getUid().equals(uid)) {
         list.remove(cart);
         }
         }
         */
        Iterator<CartVO> it = list.iterator();
        while (it.hasNext()) {
            CartVO cart = it.next();
            if (!cart.getUid().equals(uid)) {
                it.remove();
            }
        }
        return list;
    }

    @Transactional
    @Override
    public void delete(Integer cid, Integer uid, String username) {
        Cart result = cartMapper.findByCid(cid);
        // 判断查询结果是否为null
        if (result == null) {
            // 是：抛出AddressNotFoundException
            throw new CartNotFoundException("尝试访问的收货地址数据不存在");
        }

        // 判断查询结果中的uid与参数uid是否不一致(使用equals()判断)
        if (!result.getUid().equals(uid)) {
            // 是：抛出AccessDeniedException：非法访问
            throw new AccessDeniedException("非常访问");
        }

        // 根据参数aid，调用deleteByAid()执行删除
        Integer rows1 = cartMapper.deleteByCid(cid);
        if (rows1 != 1) {
            throw new DeleteException("删除收货地址数据时出现未知错误，请联系系统管理员");
        }

        // 判断查询结果中的isDefault是否为0
//        if (result.getIsDefault() == 0) {
//            return;
//        }

        // 调用持久层的countByUid()统计目前还有多少收货地址
//        Integer count = addressMapper.countByUid(uid);
//        // 判断目前的收货地址的数量是否为0
//        if (count == 0) {
//            return;
//        }

        // 调用findLastModified()找出用户最近修改的收货地址数据
//        Address lastModified = addressMapper.findLastModified(uid);
//        // 从以上查询结果中找出aid属性值
//        Integer lastModifiedAid = lastModified.getAid();
//        // 调用持久层的updateDefaultByAid()方法执行设置默认收货地址，并获取返回的受影响的行数
//        Integer rows2 = addressMapper.updateDefaultByAid(lastModifiedAid, username, new Date());
        // 判断受影响的行数是否不为1
//        if (rows2 != 1) {
//            // 是：抛出UpdateException
//            throw new UpdateException("更新收货地址数据时出现未知错误，请联系系统管理员");
//        }
    }

}
