package woowacourse.kanban.board

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import woowacourse.kanban.board.components.KanbanBoard
import woowacourse.kanban.board.components.KanbanSidebar
import woowacourse.kanban.board.components.KanbanSnackBar
import woowacourse.kanban.board.constant.MockData
import woowacourse.kanban.domain.project.KanbanProject

@Composable
fun KanbanPage(
    modifier: Modifier = Modifier,
    inputProjects: List<KanbanProject> = MockData.MOCK_PROJECTS,
    snackbarHostState: SnackbarHostState = SnackbarHostState(),
) {
    var selectedProject by remember { mutableStateOf(inputProjects.first()) }
    var selectedProjectIndex by remember { mutableIntStateOf(0) }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState, modifier = Modifier.offset(y = (-50).dp)) { data ->
                KanbanSnackBar(data)
            }
        },
        modifier = modifier,
    ) { innerPadding ->
        Row(modifier = Modifier.padding(innerPadding)) {
            KanbanSidebar(
                inputProjects,
                selectedProjectIndex = selectedProjectIndex,
                onClick = { index ->
                    selectedProjectIndex = index
                    selectedProject = inputProjects[selectedProjectIndex]
                },
            )
            KanbanBoard(
                project = selectedProject,
                boardState = BoardState(selectedProject),
                snackbarHostState = snackbarHostState,
            )
        }
    }
}

@Preview(widthDp = 1600, heightDp = 900)
@Composable
fun KanbanPagePreview() {
    KanbanPage()
}
