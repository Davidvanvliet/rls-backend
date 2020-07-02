alter table custom_message_status
    add constraint fk_customMessage_errorMessage
        foreign key (error_message_id) references error_message (id)
