package com.example.real_time_task_management.presentation.comms

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.real_time_task_management.dto.responsedto.CommentResponseDTO
import com.example.real_time_task_management.presentation.viewmodel.ServiceViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentsBottomSheet(
    state: SheetState,
    onDismiss: () -> Unit,
    taskId: Long,
) {
    val viewModel: ServiceViewModel = hiltViewModel()
    val coroutineScope = rememberCoroutineScope()
    var commentText by remember { mutableStateOf("") }

    val comments = viewModel.getCommentsPaged(taskId).collectAsLazyPagingItems()
    // Dummy comments for now
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = state,
        containerColor = Color.White,
        tonalElevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(
                text = "Comments",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // 🔸 Scrollable comments
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, fill = true)
            ) {
                items(
                    count = comments.itemCount,
                    key = { index -> comments[index]?.id ?: index }) { index ->

                    val comment = comments[index]
                    if (comment != null) {
                        CommentCard(comment)
                    } else {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("No Comments Found")
                        }
                    }
                }
                item {
                    Divider(color = Color.LightGray)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 🔸 Input row
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicTextField(
                    value = commentText,
                    onValueChange = { commentText = it },
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp),
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            if (commentText.isEmpty()) {
                                Text(
                                    text = "Add a comment...",
                                    color = Color.Gray,
                                    fontSize = 14.sp
                                )
                            }
                            innerTextField()
                        }
                    }
                )

                TextButton(
                    onClick = {
                        if (commentText.isNotBlank()) {
//                            comments.add(commentText)
                            commentText = ""
                        }
                    }
                ) {
                    Text("Post", color = MaterialTheme.colorScheme.primary)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
