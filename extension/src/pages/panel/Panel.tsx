import {
  Box,
  Button,
  ButtonGroup,
  ButtonProps,
  Card,
  CardBody,
  CardFooter,
  CardProps,
  ChakraProvider,
  DarkMode,
  Divider,
  Flex,
  FlexProps,
  Grid,
  GridItem,
  Heading,
  HeadingProps,
  Img,
  Link,
  SimpleGrid,
  Stack,
  Tag,
  TagLabel,
  Text,
  useDisclosure,
} from "@chakra-ui/react";
import { faFolder } from "@fortawesome/free-regular-svg-icons";
import { faGear, faSearch } from "@fortawesome/free-solid-svg-icons";
import { FaIcon } from "@src/components/common/FaIcon";
import theme from "@src/components/common/GlobalTheme";
import { PanelModal } from "@src/components/panel/PanelModal";
import axios from "axios";
import { useEffect, useState } from "react";
import logo from "../../assets/img/logo.png";

const Title = (props: FlexProps) => (
  <DarkMode>
    <Flex
      alignItems="center"
      justifyContent="center"
      gap={2}
      height={16}
      cursor="default"
      fontSize="xl"
      fontWeight="bold"
      bg={"gray.700"}
      color={"white"}
      {...props}
    >
      {props.children}
    </Flex>
  </DarkMode>
);

const SectionContainer = (props: FlexProps) => (
  <Flex
    boxSizing={"border-box"}
    flexDirection={"column"}
    paddingY={5}
    paddingX={2}
    sx={{
      "& > *": {
        fontSize: "md",
      },
    }}
    {...props}
  >
    {props.children}
  </Flex>
);

const SectionTitle = (props: HeadingProps) => (
  <Heading
    fontSize={"md"}
    fontWeight={"bold"}
    paddingX={4}
    paddingY={4}
    {...props}
  >
    {props.children}
  </Heading>
);

const SectionIconBtn = (props: ButtonProps) => (
  <Button justifyContent={"flex-start"} variant={"ghost"} {...props}>
    {props.children}
  </Button>
);

const TagContainer = (props: FlexProps) => (
  <Flex
    flexDirection={"row"}
    alignItems={"center"}
    justifyContent={"flex-start"}
    gap={2}
    {...props}
  >
    {props.children}
  </Flex>
);

const BookmarkCard = (props: CardProps) => (
  <Card width={"100%"} {...props}>
    <CardBody>
      <Img
        src="https://images.unsplash.com/photo-1555041469-a586c61ea9bc?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1770&q=80"
        alt="Green double couch with wooden legs"
        borderRadius="lg"
      />
      <Stack mt={6} spacing={4}>
        <Heading size="md" noOfLines={1}>
          Gradescope
        </Heading>
        <Text
          as={Link}
          size="xs"
          mt="2 !important"
          noOfLines={1}
          href="https://www.gradescope.com/"
          target="_blank"
        >
          https://www.gradescope.com/
        </Text>
        <Divider />
        <Heading size={"xs"}>
          <Flex alignItems={"center"} gap={2}>
            <FaIcon icon={faFolder} /> group
          </Flex>
        </Heading>
        <Box
          overflowX={"scroll"}
          sx={{ "::-webkit-scrollbar": { display: "none" } }}
        >
          <TagContainer width={"max-content"}>
            <Tag>tag1</Tag>
            <Tag>tag2</Tag>
            <Tag>tag3</Tag>
            <Tag>tag1</Tag>
            <Tag>tag2</Tag>
            <Tag>tag3</Tag>
            <Tag>tag1</Tag>
            <Tag>tag2</Tag>
            <Tag>tag3</Tag>
          </TagContainer>
        </Box>
        <Text noOfLines={2}>
          This sofa is perfect for modern tropical spaces, baroque inspired
          spaces, earthy toned spaces and for people who love a chic design with
          a sprinkle of vintage design.
        </Text>
      </Stack>
    </CardBody>
    <CardFooter
      justify="space-between"
      gap={4}
      sx={{
        "& > button": {
          minW: "136px",
        },
      }}
    >
      <Button minWidth="0 !important" flex="1">
        View
      </Button>
      <Button minWidth="0 !important" flex="1">
        Edit
      </Button>
    </CardFooter>
  </Card>
);

const Panel = () => {
  const [groups, setGroups] = useState([]);
  const [tags, setTags] = useState([
    "taggggg1",
    "tag2",
    "tttag3",
    "tag4",
    "taggg5",
    "tagg6",
    "tttttag7",
  ]);
  const [board, setBoard] = useState({
    bookmarks: [{}, {}, {}, {}, {}, {}],
    filter: {
      type: "all",
      value: "",
    },
  });

  const { isOpen, onOpen, onClose } = useDisclosure();

  const init = async () => {
    // request APIs
    const [groupsRes] = await Promise.allSettled([
      axios.get("http://43.201.119.242/groups", {
        headers: {
          "Content-Type": "application/json",
        },
      }),
    ]);

    // update UI
    if (groupsRes.status == "rejected") {
      console.log("group err");
    } else {
      groupsRes.value.data && setGroups(groupsRes.value.data);
    }
  };

  useEffect(() => {
    init();
  }, []);

  return (
    <ChakraProvider theme={theme}>
      <PanelModal isOpen={isOpen} onClose={onClose} />
      <Grid gridTemplateColumns="320px auto" minHeight="100vh">
        <GridItem
          display="flex"
          flexDirection="column"
          height="100%"
          bg={"gray.600"}
          color={"gray.50"}
        >
          <Title>
            <Img src={logo} height={6} />
            Bookmarker
          </Title>
          <SectionContainer>
            <SectionIconBtn
              leftIcon={<FaIcon icon={faSearch} />}
              onClick={onOpen}
            >
              Search
            </SectionIconBtn>
            <SectionIconBtn leftIcon={<FaIcon icon={faGear} />} isDisabled>
              Settings
            </SectionIconBtn>
          </SectionContainer>
          <SectionContainer>
            <SectionTitle>Groups</SectionTitle>
            {groups.map((value, index) => (
              <SectionIconBtn key={index} leftIcon={<FaIcon icon={faFolder} />}>
                {value}
              </SectionIconBtn>
            ))}
          </SectionContainer>
          <SectionContainer>
            <SectionTitle>Tags</SectionTitle>
            <TagContainer paddingX={4} paddingY={2} flexWrap={"wrap"}>
              {tags.map((value, index) => (
                <Tag as={Button} key={index} height={"auto"}>
                  <TagLabel>{value}</TagLabel>
                </Tag>
              ))}
            </TagContainer>
          </SectionContainer>
        </GridItem>
        <GridItem display="flex" flexDirection="column" padding={6}>
          <Heading as={"h1"} size={"lg"} textTransform={"capitalize"} mb={6}>
            {board.filter.value ? board.filter.value : board.filter.type}
          </Heading>
          <SimpleGrid minChildWidth="280px" spacing={4}>
            {board.bookmarks.map((value, index) => (
              <BookmarkCard key={index} />
            ))}
          </SimpleGrid>
        </GridItem>
      </Grid>
    </ChakraProvider>
  );
};

export default Panel;
