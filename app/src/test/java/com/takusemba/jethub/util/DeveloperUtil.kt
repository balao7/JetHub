package com.takusemba.jethub.util

import com.takusemba.jethub.model.Developer

fun createDeveloper(
  id: Int = -1,
  login: String = "",
  avatarUrl: String = "",
  name: String = "",
  company: String = "",
  blog: String = "",
  location: String = "",
  email: String = "",
  bio: String = "",
  publicRepositoriesCount: Int = 0,
  publicGistsCount: Int = 0,
  followersCount: Int = 0,
  followingCount: Int = 0
) = Developer(
  id = id,
  login = login,
  avatarUrl = avatarUrl,
  name = name,
  company = company,
  blog = blog,
  location = location,
  email = email,
  bio = bio,
  publicRepositoriesCount = publicRepositoriesCount,
  publicGistsCount = publicGistsCount,
  followersCount = followersCount,
  followingCount = followingCount
)