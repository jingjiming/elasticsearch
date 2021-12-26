package com.alpha.common.util.lanxin.beans;

import com.alpha.common.util.lanxin.beans.CardText;
import com.alpha.common.util.lanxin.beans.Message;

/**
 * Created by jiming.jing on 2020/7/18.
 */
public class CardTextMessage extends Message {

    private CardText cardText;

    public CardText getCardText() {
        return cardText;
    }

    public void setCardText(CardText cardText) {
        this.cardText = cardText;
    }
}
