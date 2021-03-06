/**
 * Leaf node for BinTree class.
 * 
 * @author  Bimal Gaudel
 * 
 * @version Apr 22, 2020
 *
 */
public class BinTreeNodeLeaf implements BinTreeNode {


    /**
     * The maximum number of records allowed in the node. For the current
     * project it is three.
     */
    protected static final int MAX_RECORDS = 3;


    /** List of records stored by this node. */
    private LinkedList<AirObject> records;


    /** Default constructor. */
    public BinTreeNodeLeaf() {
        records = new LinkedList<AirObject>();
    }


    /**
     * Give access the records list to the classes in the same package.
     * 
     * @return The linked list of records.
     */
    protected LinkedList<AirObject> getRecords() {
        return records;
    }


    /**
     * Check if the node is a leaf node.
     * 
     * @return True as it is a leaf node.
     */
    @Override
    public boolean isLeaf() {
        return true;
    }


    /**
     * Check if the node is empty.
     * 
     * @return True if the node contains no records in it.
     */
    public boolean isEmpty() {
        return records.getCount() == 0;
    }


    /**
     * Insert an AirObject into the tree.
     * 
     * @param  airObject The AirObject to be inserted.
     * @param  box       The Box information passed to current node to help
     *                   decide where to insert the AirObject.
     * @param  nodeLevel The level of the current node in the tree. The root is
     *                   at the level zero and the level increases down the
     *                   tree.
     * 
     * @return           The node after the insertion.
     */
    @Override
    public BinTreeNode insert(AirObject airObject, BinBox box, int nodeLevel) {
        // start by inserting the record to the records list
        records.insert(airObject);

        // now we check if we have violated the decomposition rule and should
        // split the current node
        if (!shouldSplit()) {
            return this;
        }

        // if we are here, we should split the current node
        BinTreeNode splitNode = new BinTreeNodeInternal();
        for (records.moveToHead(); !records.atEnd(); records.curseToNext()) {
            splitNode =
                    splitNode.insert(records.yieldCurrNode(), box, nodeLevel);
        }

        return splitNode;
    }


    /**
     * Delete an AirObject from the tree.
     * 
     * @param  airObject The AirObject to be deleted.
     * @param  box       The Box information passed to current node to help
     *                   locate the AirObject.
     * @param  nodeLevel The level of the current node in the tree. The root is
     *                   at the level zero and the level increases down the
     *                   tree.
     * 
     * @return           The node after the deletion.
     */
    @Override
    public BinTreeNode delete(AirObject airObject, BinBox box, int nodeLevel) {
        records.remove(airObject);
        if (isEmpty()) {
            return BinTreeNodeFlyweight.getInstance();
        }
        // else
        return this;
    }


    /**
     * Check if this leaf node in the current state should split according to
     * the decomposition rule. The decomposition rule for this project is that
     * there can be at most three AirObject records in the node when not all of
     * them intersect a common point in space. If all the AirObjects' present
     * do, however, have a common intersection, there can be arbitrary number of
     * records.
     *
     * @return True if the node should split according to the decomposition
     *         rule.
     */
    private boolean shouldSplit() {
        if (records.getCount() <= MAX_RECORDS) {
            // not enough records present to split
            // return false to indicate no splitting required
            return false;
        }

        // iterating through each of the records present and seeing each of
        // their box intersects with every other record's box
        records.moveToHead();
        Box isecBox = records.yieldCurrNode().getBox();
        for (; !records.atEnd(); records.curseToNext()) {
            // the box of the current record in the list
            Box recordBox = records.yieldCurrNode().getBox();
            if (!recordBox.intersects(isecBox)) {
                // found a record with a box that doesn't intersect
                // with the others
                return true;
            }
            // update the intersection
            isecBox = recordBox.getIntersection(isecBox);
        }

        // If we reach here, it implies all the record's boxes have a common
        // intersection. Return false to indicate no splitting required
        return false;
    }

}
