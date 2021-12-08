package com.example.practice1.presentation.coin_detail.components

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.practice1.data.remote.dto.TeamMember
import org.junit.Rule
import org.junit.Test

class TeamListItemKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()


    @Test
    fun teamMemberNameTest() {

        //given
        val mockTeamMember = mockTeamMember(name = "Mock TeamMember!")

        //when
        composeTestRule.setContent { TeamListItem(teamMember = mockTeamMember) }

        //then
        composeTestRule.onNodeWithText(mockTeamMember.name).assertIsDisplayed()
    }

    @Test
    fun teamMemberPositionTest() {

        //given
        val mockTeamMember = mockTeamMember(position = "Mock TeamMemberPosition!")

        //when
        composeTestRule.setContent { TeamListItem(teamMember = mockTeamMember) }

        //then
        composeTestRule.onNodeWithText(mockTeamMember.position).assertIsDisplayed()
    }

    @Test
    fun spacerTest() {

        //given
        val mockTeamMember = mockTeamMember()

        //when
        composeTestRule.setContent { TeamListItem(teamMember = mockTeamMember) }


        //then
        composeTestRule.onNodeWithTag("spacer").assertExists()
    }

    private fun mockTeamMember(id: String = "", name: String = "", position: String = "") =
        TeamMember(id, name, position)

}