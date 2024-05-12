package com.example.locamail;

import android.content.Context
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.*

public class EmailService {
    fun getEmails(context: Context?): List<EmailModel> {

        val dbHandler: DBHandler = DBHandler(context);
        val emails: List<EmailModel>;

        if(!dbHandler.existEmails()) {
            val random = Random()
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

            List(20) { index ->
                val currentDate = Calendar.getInstance()
                currentDate.add(Calendar.DAY_OF_MONTH, -random.nextInt(30)) // Subtrai até 30 dias da data atual
                val date = dateFormat.format(currentDate.time)

                dbHandler.addNewEmail(
                    sender = "Sender $index",
                    receive = "Receive $index",
                    subject = "Subject $index - Enviado",
                    preview = "Preview of email $index",
                    content = "content $index",
                    markup = "markup $index",
                    important = if (index%2 == 0) 1 else 0,
                    deleted = 0,
                    spam = 0,
                    sended = 1,
                    received = 0,
                    date = date,
                    read = 0
                )
            }
            List(20) { index ->
                val currentDate = Calendar.getInstance()
                currentDate.add(Calendar.DAY_OF_MONTH, -random.nextInt(30)) // Subtrai até 30 dias da data atual
                val date = dateFormat.format(currentDate.time)
                val contexto = if (index%2 == 0) "spam" else "important"
                dbHandler.addNewEmail(
                    sender = "Sender $index",
                    receive = "Receive $index",
                    subject = "Subject $index - $contexto Recebido",
                    preview = "Preview of email $index",
                    content = "content $index",
                    markup = "markup $index",
                    important = if (index%2 == 0) 0 else 1,
                    deleted = 0,
                    spam = if (index%2 == 0) 1 else 0,
                    sended = 0,
                    received = 1,
                    date = date,
                    read = 0
                )
            }
            List(30) { index ->
                val currentDate = Calendar.getInstance()
                currentDate.add(Calendar.DAY_OF_MONTH, -random.nextInt(30)) // Subtrai até 30 dias da data atual
                val date = dateFormat.format(currentDate.time)
                val contexto = if (index%2 == 0) "Enviado" else "Recebido"
                if(index > 20)
                dbHandler.addNewEmail(
                    sender = "Sender $index",
                    receive = "Receive $index",
                    subject = "Subject $index",
                    preview = "Preview of email $index - $contexto",
                    content = "content $index",
                    markup = "markup $index",
                    important = if (index%2 == 0) 0 else 1,
                    deleted = 1,
                    spam = 0,
                    sended = if (index%2 == 0) 1 else 0,
                    received = if (index%2 == 0) 0 else 1,
                    date = date,
                    read = 0
                )
            }
        }
        emails = dbHandler.readEmails()?.toList() ?: emptyList();
        return emails
    }
}
